
var storage = window.localStorage;

var CDR_TEMPLATE_ROW = `
<tr>
<td>{{TYPE}}</td>
<th scope="row">{{NAME}}</th>
<td>@tg2sip</td>
<td>{{DATE}}</td>
<td>{{DURATION}}s</td>
`
function loadCdr(){
    Tg2SipDemo.getCallLog()
        .then(calls => {
            var html = ""

            calls.forEach(cdr => {
                console.log(cdr.name);
                var name = cdr.name;
                if (cdr.username !== 'anonymous'){
                    name += ` - <a target="blank" href="https://t.me/${cdr.username}">${cdr.username}</a>`
                }

                html+= CDR_TEMPLATE_ROW.replace('{{TYPE}}', cdr.callType == 'inbound' ? 'Outbound' : 'Inbound')
                    .replace('{{NAME}}', name)
                    .replace('{{DATE}}', cdr.date)
                    .replace('{{DURATION}}', cdr.duration);
            });

            document.getElementById('calllog-details').innerHTML = html;

        });
}

function listeners() {

    document.addEventListener('tg2sip.telegram.auth', function (e) {
        var user = e.detail;

        user.firstName = user.first_name;
        user.lastName = user.last_name;
        user.photoUrl = user.photo_url;
        user.authDate = user.auth_date;

        Tg2SipDemo.checkTelegramAuth(user)
                .then( res => {
                    Tg2SipDemo.setToken(res.token);
                    storage.setItem('token', res.token);
                    window.location = "/";
                })
                .catch(err => {
                    console.log(err);
                });
        }, false);

        document.addEventListener('tg2sip.telegram.logout', function (e) {
            storage.removeItem('token');
            window.location = "/";
        });
  }

  function loadCredential(){
    var token = storage.getItem('token');
    Tg2SipDemo.setToken(token);

    Tg2SipDemo.getConfig()
        .then( res => {
            console.log(res);
            // not a valid telegram userId
            if (res.id == -1){

                document.getElementById('btn-callme').style.display = 'none';
                document.getElementById('btn-calls').style.display = 'none';

                var telegramAuthWidget = document.createElement('script');
                telegramAuthWidget.src = 'https://telegram.org/js/telegram-widget.js?9'
                telegramAuthWidget.setAttribute('data-telegram-login', res.botName);
                telegramAuthWidget.setAttribute('data-size', 'large');
                telegramAuthWidget.setAttribute('data-onauth', 'telegramAuth(user)');

                var doc = document.getElementById('form-auth');
                doc.appendChild(telegramAuthWidget);
            } else {
                console.log('Valid User');
                document.getElementById('userName').textContent = res.name;
                document.getElementById('userInfo').style.display = 'block';
                document.getElementById('userImage').style.backgroundImage = `url(${res.photo})`;
            }
        })
        .catch( err => {
            storage.removeItem('token');
            window.location = "/";
            console.log(err);
        });
}

// when window is ready loaded, validate session
window.onload = async function(){
    loadCredential();
    listeners();
    loadCdr();
}

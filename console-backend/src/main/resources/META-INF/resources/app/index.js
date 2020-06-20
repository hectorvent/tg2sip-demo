
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
                })
                .catch(err => {
                    console.log(err);
                });
        }, false);
  }

  function loadCredential(){
    var token = storage.getItem('token');

    if (token == null || token === undefined){
        var telegramAuthWidget = document.createElement('script');
        telegramAuthWidget.src = 'https://telegram.org/js/telegram-widget.js?9'
        telegramAuthWidget.setAttribute('data-telegram-login', 'tg2sipdemobot');
        telegramAuthWidget.setAttribute('data-size', 'large');
        telegramAuthWidget.setAttribute('data-onauth', 'telegramAuth(user)');

        var doc = document.getElementById('form-auth');
        doc.appendChild(telegramAuthWidget);
    } else {
        Tg2SipDemo.setToken(token);
    }
}

// when window is ready loaded, validate session
window.onload = async function(){
    loadCredential();
    listeners();
    loadCdr();
}

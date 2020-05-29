require 'db'

function db_get_user(id)
    local user = nil
    db:query("SELECT * FROM telegram_user WHERE telegram_user_id = " .. id, function(row)
        freeswitch.consoleLog('info', 'User Name: ' .. row.telegram_firstname)
        user = row
    end)
    return user
end

function db_start_call_cdr(user)

    local query = db:prepare([[
        INSERT INTO cdr ( call_type, telegram_id, telegram_name, telegram_phone, telegram_username, call_id )
        VALUES (':call_type', :telegram_id, ':telegram_name', ':telegram_phone', ':telegram_username', ':call_id')
        ]], {
            call_type = user.call_type,
            telegram_id = user.telegram_id,
            telegram_name = user.telegram_name,
            telegram_phone = user.telegram_phone,
            telegram_username = user.telegram_username,
            call_id = user.call_id
        })

    db:query(query, function(d)
        freeswitch.consoleLog('info', inspect(d))
    end)
end


function db_end_call_cdr(call_id)
    db:query("UPDATE cdr SET end_date = CURRENT_TIMESTAMP where call_id ='"..call_id.."'", function(d)
        freeswitch.consoleLog('info', inspect(d))
    end)
end

require 'db'

function db_get_user(id)
    local user = nil
    db:query("SELECT * FROM telegram_user WHERE telegram_id = " .. id, function(row)
        user = row
    end)
    return user
end

function db_start_call_cdr(cdr)

    local query = db:prepare([[
        INSERT INTO cdr ( call_type, telegram_id, call_id ) VALUES (':call_type', :telegram_id, ':call_id')
        ]], {
            call_type = cdr.call_type,
            telegram_id = cdr.telegram_id,
            call_id = cdr.call_id
        })

    db:query(query)
end

function db_end_call_cdr(cdr)
    db:query("UPDATE cdr SET end_date = CURRENT_TIMESTAMP where call_id ='" .. cdr.call_id .. "'")
end

function db_register_user(user)

    local query = db:prepare([[
        INSERT INTO telegram_user ( telegram_id, telegram_name, telegram_phone, telegram_username )
        VALUES (:telegram_id, ':telegram_name', ':telegram_phone', ':telegram_username')
        ]], {
            telegram_id = user.telegram_id,
            telegram_name = user.telegram_name,
            telegram_phone = user.telegram_phone,
            telegram_username = user.telegram_username,
        })

    db:query(query)
end

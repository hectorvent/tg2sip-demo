require 'db'

function get_user_by_telegram_id(id)
    local user = nil
    db:query("SELECT * FROM users WHERE telegram_id = " .. id, function(row)
        user = row
    end)
    return user
end

function save_user(user)

    local query = db:prepare([[
        INSERT INTO users ( telegram_id, telegram_name, telegram_phone, telegram_username )
        VALUES (:telegram_id, ':telegram_name', ':telegram_phone', ':telegram_username')
        RETURNING id
        ]], {
            telegram_id = user.telegram_id,
            telegram_name = user.telegram_name,
            telegram_phone = user.telegram_phone,
            telegram_username = user.telegram_username,
        })

    db:query(query, function(row)
        user.id = row.id
    end)
end

function save_cdr(cdr)

    local query = db:prepare([[
        INSERT INTO cdr ( call_type, user_id, call_id ) VALUES (':call_type', :user_id, ':call_id')
        ]], {
            call_type = cdr.call_type,
            user_id = cdr.user_id,
            call_id = cdr.call_id
        })

    db:query(query)
end

function end_cdr(cdr)
    db:query("UPDATE cdr SET end_date = CURRENT_TIMESTAMP where call_id ='" .. cdr.call_id .. "'")
end

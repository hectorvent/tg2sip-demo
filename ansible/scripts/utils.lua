
function get_user_from_headers()

    local call_id = session:getVariable('uuid')
    local telegram_id = session:getVariable("sip_h_X-TG-ID")
    local telegram_username = session:getVariable("sip_h_X-TG-Username") or ''
    local telegram_firstname = session:getVariable("sip_h_X-TG-FirstName") or ''
    local telegram_lastname = session:getVariable("sip_h_X-TG-LastName") or ''
    local telegram_phone = session:getVariable("sip_h_X-TG-Phone") or ''

    return {
        call_id = call_id,
        telegram_id = telegram_id,
        telegram_username = telegram_username,
        telegram_name = telegram_firstname .. ' ' .. telegram_lastname,
        telegram_phone = telegram_phone
    }

end

function get_telegram_id_from_headers()
    telegram_id = session:getVariable("sip_h_X-TG-ID")
    return telegram_id
end
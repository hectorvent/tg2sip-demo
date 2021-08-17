
-- appending this path as package path
package.path = package.path .. ";/usr/share/freeswitch/scripts/tg2sip/?.lua"

require 'repository'
require 'utils'

local inspect = require('inspect')
local audio_directory = '/usr/share/freeswitch/sounds/tg2sip/'

function hangup_hook(s, status)
    local call_id = session:getVariable('uuid')

    if call_id ~= nil then
        db_end_call_cdr({ call_id = callid })
    end
    freeswitch.consoleLog('info', 'Ending call')
end

function input_callback(session, type, obj, arg)
    freeswitch.consoleLog('info', 'Get an event')

    if type == "dtmf" then
        if obj.digit == "1" then

        end
    else
        freeswitch.consoleLog('info', obj:serialize("xml"))
    end

    return "true"
end

-- mozart_no1.wav , https://www.youtube.com/watch?v=b4IXXpTHjok&feature=youtu.be
-- beethoven_no5.wav
local default_sound = audio_directory .. 'mozart_no1.wav';
local telegram_id = get_telegram_id_from_headers()
local user = get_user_by_telegram_id(telegram_id)

if user ~= nil then

    if user.default_sound ~= nil and user.default_sound ~= "" then
        default_sound = audio_directory .. user.default_sound .. '.wav'
    end

    -- TODO: check if the user has redirected a call to:
else
    user = get_user_from_headers()
    save_user(user)
    -- TODO: INSERT default user
end

freeswitch.consoleLog('info', inspect(user))

local call_id = session:getVariable('uuid')
local cdr = {
    call_type =  'inbound',
    user_id = user.id,
    call_id = call_id
}

save_cdr(cdr)

session:answer()
session:setHangupHook('hangup_hook')
session:setInputCallback("input_callback")
session:execute("playback", default_sound)

freeswitch.consoleLog('info', inspect(cdr))
-- if the music end before user hangup update ending time on CDR
end_cdr(cdr)

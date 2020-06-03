
-- appending this path as package path
package.path = package.path .. ";/usr/share/freeswitch/scripts/tg2sip/?.lua"

require 'repository'
require 'utils'

local inspect = require('inspect')
local audio_directory = '/usr/share/freeswitch/sounds/tg2sip/'

function hangup_hook(s, status)
    local call_id = session:getVariable('uuid')
    db_end_call_cdr({ call_id = call_id })
    freeswitch.consoleLog('info', 'Ending call')
end

function input_callback(session, type, obj, arg)
    freeswitch.consoleLog('info', 'Entando al dtmfffffff')

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
local default_sound = audio_directory..'mozart_no1.wav';
local telegram_id = get_telegram_id_from_headers()
local user = db_get_user(telegram_id)

if user ~= nil then

    if user.default_sound ~= nil and user.default_sound ~= "" then
        default_sound = audio_directory .. user.default_sound .. '.wav'
    end

    -- TODO: check if the user has redirected a call to:
else
    user = get_user_from_headers()
    db_register_user(user)
    -- TODO: INSER default user
end

freeswitch.consoleLog('info', inspect(user))

local call_id = session:getVariable('uuid')
local cdr = {
    call_type =  'inbound',
    telegram_id = user.telegram_id,
    call_id = call_id
}

db_start_call_cdr(cdr)

session:answer()
session:setHangupHook('hangup_hook')
session:setInputCallback("input_callback")
session:execute("playback", default_sound)

freeswitch.consoleLog('info', inspect(cdr))
-- if the music end before user hangup update ending time on CDR
db_end_call_cdr(cdr)
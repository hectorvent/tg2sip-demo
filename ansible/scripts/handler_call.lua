
-- appending this path as package path
package.path = package.path .. ";/usr/share/freeswitch/scripts/tg2sip/?.lua"

require 'repository'
require 'utils'

local inspect = require('inspect')
local audio_directory = '/usr/share/freeswitch/sounds/tg2sip/'

function hangup_hook(s, status)
    local call_id = session:getVariable('uuid')
    db_end_call_cdr(call_id)
    freeswitch.consoleLog('info', 'Ending call')
end

-- mozart_no1.wav , https://www.youtube.com/watch?v=b4IXXpTHjok&feature=youtu.be
-- beethoven_no5.wav
local default_mediafile = audio_directory..'mozart_no1.wav';
local telegram_id = get_telegram_id_from_headers()
local user = nil --db_get_user(telegram_id)

if user ~= nil then

    if user.default_sound ~= nil then
        default_mediafile = audio_directory .. user.default_sound .. '.wav'
    end

    -- TODO: check if the user has redirected a call to:
else
    user = get_user_from_headers()
    -- TODO: INSER default user
end

user.call_type = 'inbound'
freeswitch.consoleLog('info', inspect(user))

db_start_call_cdr(user)

session:answer()
session:setHangupHook('hangup_hook')
session:execute("playback", default_mediafile)


-- session.setInputCallback(input_callback)
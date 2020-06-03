require 'db_config'

db = {}

local function get_connection()
    local conn = freeswitch.Dbh(dburl)
    assert(conn:connected())
    return conn
end

function db:prepare(query, params)
    for k,v in pairs(params) do
        query = string.gsub(query, ":"..k, v)
    end
    return query
end

function db:query(query, cb)
    local conn = get_connection()
    cb = cb or function(r) end
    conn:query(query, cb)
    conn:release()
end

Tg2SipDemo =  {
    basePath: '',

    setToken(token){
        Request.token  = token;
    },
    getConfig() {
        return Request.get(`${this.basePath}/config`);
    },
    getCallLog() {
       return Request.get(`${this.basePath}/cdr`);
    },
    checkTelegramAuth(user){
        return Request.post(`${this.basePath}/auth/check`, user);
    }
}


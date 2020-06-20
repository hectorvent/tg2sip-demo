
Tg2SipDemo =  {
    basePath: '',

    setToken(token){
        Request.token  = token;
    },
    // User Methods
    getCallLog() {
       return Request.get(`${this.basePath}/cdr`);
    },

     checkTelegramAuth(user){
        return Request.post(`${this.basePath}/auth/check`, user);
    }
}


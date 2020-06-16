import Request from './Request'

export default class ApiService {

    constructor(basePath, token){
        this.httpClient = new Request(token);
        this.basePath = basePath;
    }

    // User Methods
    async getConfig() {
       return this.httpClient.get(`${this.basePath}/config`);
    }

    async saveConfig(config) {
        return this.httpClient.put(`${this.basePath}/config`);
    }

    async logout() {
        return this.httpClient.delete(`${this.basePath}/logout`);
    }
    async getCallLog() {
        return this.httpClient.get(`${this.basePath}/cdr`);
    }

    async callMe() {
        return this.httpClient.delete(`${this.basePath}/post/${id}/like`);
    }
}


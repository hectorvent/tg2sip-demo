
Request = {
  token: null,
  post(path, data){

    var headers = {
        'Content-Type': 'application/json'
    }

    if (this.token !== undefined && this.token != null){
        headers["Authorization"] = `Bearer ${this.token}`;
    }

    return fetch(path, {
        method: 'POST',
        body: JSON.stringify(data),
        headers: headers
      }).then(function(response) {
        if (!response.ok) {
            throw Error(response.statusText);
        }
        return response;
      }).then( res => res.json());
  },

  put(path, data){

        var headers = {
            'Content-Type': 'application/json'
          }

        if (this.token !== undefined && this.token != null){
            headers["Authorization"] = `Bearer ${this.token}`;
        }


        return fetch(path, {
          method: 'PUT',
          body: JSON.stringify(data),
          headers: headers
        }).then(function(response) {
          if (!response.ok) {
              throw Error(response.statusText);
          }
          return response;
        }).then( res => res.json());
  },

  get(path){

    var headers = {
        'Content-Type': 'application/json'
      }

    if (this.token !== undefined && this.token != null){
        headers["Authorization"] = `Bearer ${this.token}`;
    }

    return fetch(path, {
      method: 'GET',
      headers: headers
    }).then(function(response) {
      if (!response.ok) {
          throw Error(response.statusText);
      }
      return response;
    }).then( res => res.json());
  },

  delete(path){

    var headers = {
      'Content-Type': 'application/json'
    }

    if (this.token !== undefined && this.token != null){
        headers["Authorization"] = `Bearer ${this.token}`;
    }

    return fetch(path, {
      method: 'DELETE',
      headers: headers
    }).then(function(response) {
      if (!response.ok) {
          throw Error(response.statusText);
      }
      return response;
    }).then( res => res.json());
    }
}

import {HttpClient} from "@angular/common/http";
import {Injectable} from "@angular/core";

@Injectable()
export class LoginService
{
  baseURL = 'http://localhost:8080/login'

  constructor(private httpClient: HttpClient)
  {
  }

  login(username: string, password: string)
  {
    return this.httpClient.post(this.baseURL, '', {
      params: {username: username, password: password},
      observe: 'response',
      withCredentials: true
    })
  }
}

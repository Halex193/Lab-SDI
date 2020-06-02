import {Component, OnInit} from '@angular/core';
import {CookieService} from "ngx-cookie-service";
import {Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit
{
  title = 'Movie rental software';
  baseURL = 'http://localhost:8080/api/logout'
  logged: boolean = false

  constructor(private cookieService: CookieService, private router: Router, private httpClient: HttpClient)
  {
  }

  ngOnInit()
  {
    this.logged = this.loggedIn()
  }

  loggedIn()
  {
    let loggedIn: boolean = this.cookieService.check("authenticated");
    if (!loggedIn && !(this.router.url === '/login' || this.router.url === '/'))
    {
      this.router.navigate(['../login'])
    }
    return loggedIn
  }

  logout()
  {

    return this.httpClient.post(this.baseURL, '', {
      observe: 'response',
      withCredentials: true
    }).subscribe(() => this.cookieService.delete("authenticated"))
  }

  reportsAvailable()
  {
    return this.cookieService.get("authenticated") === "admin"
  }
}

import {Component, OnInit} from '@angular/core';
import {CookieService} from "ngx-cookie-service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent
{
  title = 'Movie rental software';

  constructor(private cookieService: CookieService, private router: Router)
  {
  }

  loggedIn()
  {
    let loggedIn: boolean = this.cookieService.check("authenticated");
    if (!loggedIn && !(this.router.url === '/login' || this.router.url === '/'))
    {
      this.router.navigate(['/login'])
    }
    return loggedIn
  }

  logout()
  {
    this.cookieService.delete("authenticated")
  }

  reportsAvailable()
  {
    return this.cookieService.get("authenticated") === "admin"
  }
}

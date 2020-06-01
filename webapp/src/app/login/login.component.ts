import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {LoginService} from "./login.service";
import {CookieService} from "ngx-cookie-service";
import {ReportService} from "../reports/report.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit
{

  username: string = ""
  password: string = ""
  error: string = ""

  constructor(protected service: LoginService, protected router: Router, private cookieService: CookieService, private reportService: ReportService)
  {
  }

  ngOnInit(): void
  {
  }

  login()
  {
    this.service.login(this.username, this.password)
      .subscribe((result) =>
      {
        if (result.status == 200)
        {
          this.reportService.getClientGenres().subscribe(
            () => this.cookieService.set("authenticated", "admin"),
            () => this.cookieService.set("authenticated", "basic")
          )
          this.router.navigate(['/clients'])
        }
      }, () => this.error = "Invalid credentials!")
  }

}

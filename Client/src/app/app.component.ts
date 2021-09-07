import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/shared/services/auth.service';
import { changedKeys } from 'src/shared/utils/changed-keys';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'scala-project-client';
  constructor(private authService: AuthService) {}
  ngOnInit(): void {
    this.authService.isLoggedIn;
  }
}
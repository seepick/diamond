import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { JsonPipe } from '@angular/common';
import { DiamondUser } from '@diamond/sdk';

@Component({
  selector: 'app-home',
  imports: [RouterLink, MatButtonModule, MatCardModule, JsonPipe],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
})
export class HomeComponent {
  sampleUser: DiamondUser = {
    id: '12345',
    name: 'Christoph',
    email: 'christoph@diamond.dev',
    role: 'admin',
    createdAt: new Date(),
  };
}

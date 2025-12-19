import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-found-route',
  imports: [RouterLink, MatButtonModule],
  templateUrl: './found-route.component.html',
  styleUrl: './found-route.component.scss',
})
export class FoundRouteComponent {}

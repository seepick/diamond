import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { FoundRouteComponent } from './found-route/found-route.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'found', component: FoundRouteComponent },
];

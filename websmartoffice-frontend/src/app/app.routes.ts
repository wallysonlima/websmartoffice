import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { HomeComponent } from './pages/home/home.component';
import { AuthGuard } from './guard/auth.guard'; 

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'main', component: HomeComponent, canActivate: [AuthGuard] }, // ✅ Página protegida pelo AuthGuard
  { path: '', redirectTo: '/login', pathMatch: 'full' }, // ✅ Redireciona para login por padrão
  { path: '**', redirectTo: '/login' } // ✅ Qualquer rota inválida leva para login
];

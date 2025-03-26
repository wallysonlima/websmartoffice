import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { HomeComponent } from './pages/home/home.component';
import { PersonRegisterComponent } from './pages/person-register/person-register.component';
import { PropertyRegisterComponent } from './pages/property-register/property-register.component';
import { BuyComponent } from './pages/buy/buy.component';
import { ContractsComponent } from './pages/contracts/contracts.component';
import { AuthGuard } from './guard/auth.guard'; 

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register/person', component: PersonRegisterComponent },
  { path: 'register/property', component: PropertyRegisterComponent },
  { path: 'buy/property', component: BuyComponent },
  { path: '/list/contract', component: ContractsComponent },
  { path: 'main', component: HomeComponent, canActivate: [AuthGuard] }, // ✅ Página protegida pelo AuthGuard
  { path: '', redirectTo: '/login', pathMatch: 'full' }, // ✅ Redireciona para login por padrão
  { path: '**', redirectTo: '/login' } // ✅ Qualquer rota inválida leva para login
];

import { Component, OnInit } from '@angular/core';
import { AuthService } from './../../services/auth.service';
import { Router } from '@angular/router';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-home',
  standalone: true, // ✅ Mantendo como Standalone
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
  imports: [CommonModule, RouterModule] // ✅ Importando RouterModule para usar <router-outlet>
})
export class HomeComponent implements OnInit {
  userSession: any = null;
  
  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    console.log('Aplicação iniciada!'); // ✅ Debug para ver se o componente está sendo carregado
    const session = localStorage.getItem('userSession');
    if ( session ) {
      this.userSession = JSON.parse(session);
    }
  }

  logout() {
    this.authService.logout().subscribe(() => {
      this.router.navigate(['/login']); // ✅ Redireciona para login ao sair
    });
  }
}

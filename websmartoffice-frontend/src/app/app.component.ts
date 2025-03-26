import { Component, OnInit } from '@angular/core';
import { AuthService } from './services/auth.service';
import { Router } from '@angular/router';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true, // ✅ Mantendo como Standalone
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  imports: [CommonModule, RouterModule] // ✅ Importando RouterModule para usar <router-outlet>
})
export class AppComponent implements OnInit {
  userSession: any = null;
  isAdmin = false; // ✅ Define se o usuário é admin

  constructor(private authService: AuthService, private router: Router) {
    this.loadSession();
    this.checkUserRole();
  }

  loadSession() {
    const savedSession = localStorage.getItem('userSession');
    if (savedSession) {
      this.userSession = JSON.parse(savedSession);
      console.log('🔹 Sessão carregada:', this.userSession);
    }
  }

  ngOnInit(): void {
    console.log('Aplicação iniciada!'); // ✅ Debug para ver se o componente está sendo carregado
    this.loadSession();
    this.checkUserRole();
  }

  logout() {
    this.authService.logout().subscribe(() => {
      this.router.navigate(['/login']); // ✅ Redireciona para login ao sair
    });
  }

  checkUserRole() {
    const roles = this.authService.getUserRole();
    this.isAdmin = roles.includes('ROLE_ADMIN'); // ✅ Verifica se o usuário tem a role ADMIN
  }
}

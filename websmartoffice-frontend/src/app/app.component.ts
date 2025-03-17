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

  constructor(private authService: AuthService, private router: Router) {
    this.loadSession();
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
  }

  logout() {
    console.log('🔹 Logout iniciado...');
    
    this.authService.logout().subscribe({
      next: () => {
        console.log('✅ Logout realizado com sucesso!');
        localStorage.removeItem('userSession');
        setTimeout(() => {
          window.location.href = "/login"; // ✅ Redirecionamento direto para evitar erros
        }, 500);
      },
      error: (err) => {
        console.error('❌ Erro ao fazer logout:', err);
      }
    });
  }
}

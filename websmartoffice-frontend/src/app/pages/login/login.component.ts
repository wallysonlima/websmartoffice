import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService } from './../../services/auth.service';
import { UserService } from './../../services/UserService';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  imports: [CommonModule, FormsModule] // ✅ Garante que [(ngModel)] funciona
})
export class LoginComponent {
  loginData = { email: '', password: '' }; // ✅ Alterado para usar email

  constructor(private authService: AuthService, private router: Router, private userService: UserService) {}

  onSubmit() {
    console.log('Tentando login com:', this.loginData); // ✅ Debug no console

    this.authService.login(this.loginData).subscribe({
      next: (userData) => {
        localStorage.setItem('userEmail', userData.email); // ✅ Adicionado!
        localStorage.setItem('userRole', JSON.stringify(userData.roles)); 
        this.setUserSession(userData.email)
      },
      error: () => {
        alert('E-mail ou senha inválidos!'); // ✅ Mostra erro ao usuário
      }
    });
  }

  private setUserSession(email: string) {
    console.log('🔄 Buscando dados completos do usuário...');

    this.userService.getUserByEmail(email).subscribe({
      next: (userData) => {
        localStorage.setItem('userSession', JSON.stringify(userData));
        console.log('✅ Sessão de usuário carregada:', userData);
        this.router.navigate(['/main']); // ✅ Redireciona só aqui!
      },
      error: (err) => {
        console.error('❌ Erro ao buscar dados do usuário:', err);
        alert('Erro ao carregar dados do usuário. Tente novamente.');
      }
    });
  }
}



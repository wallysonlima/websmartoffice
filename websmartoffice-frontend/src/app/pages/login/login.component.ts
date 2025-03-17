import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService } from './../../services/auth.service';
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

  constructor(private authService: AuthService, private router: Router) {}

  onSubmit() {
    console.log('Tentando login com:', this.loginData); // ✅ Debug no console

    this.authService.login(this.loginData).subscribe({
      next: (userData) => {
        localStorage.setItem('userSession', JSON.stringify(userData)); 
        this.router.navigate(['/main']); // ✅ Redireciona após login
      },
      error: () => {
        alert('E-mail ou senha inválidos!'); // ✅ Mostra erro ao usuário
      }
    });
  }
}



import { Component, OnInit } from '@angular/core';
import { PersonService } from '../../services/person.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-person-register',
  standalone: true,
  templateUrl: './person-register.component.html',
  styleUrls: ['./person-register.component.scss'],
  imports: [CommonModule, FormsModule]
})
export class PersonRegisterComponent {
  
  personData: any = {
    name: '',
    email: '',
    password: '',
    phoneNumber: '',
    dateBirth: '',
    gender: '',
    cpf: '',
    rg: '',
    civilState: '',
    role: '',
    bankAccount: {
      bankCpf: '',
      privateKey: '',
      ethAddress: '',
      balance: ''
    }
  };

  
  errors: any = {}; // ✅ Armazena mensagens de erro
  successMessage: string = '';

  constructor(private personService: PersonService, private router: Router) {}

  // ✅ Validação de CPF
  isValidCPF(cpf: string): boolean {
    cpf = cpf.replace(/[^\d]+/g, ''); // Remove caracteres não numéricos
    if (cpf.length !== 11 || /^(\d)\1+$/.test(cpf)) return false;

    let sum = 0, remainder;
    for (let i = 0; i < 9; i++) sum += parseInt(cpf.charAt(i)) * (10 - i);
    remainder = (sum * 10) % 11;
    if (remainder === 10 || remainder === 11) remainder = 0;
    if (remainder !== parseInt(cpf.charAt(9))) return false;

    sum = 0;
    for (let i = 0; i < 10; i++) sum += parseInt(cpf.charAt(i)) * (11 - i);
    remainder = (sum * 10) % 11;
    if (remainder === 10 || remainder === 11) remainder = 0;
    return remainder === parseInt(cpf.charAt(10));
  }

  // ✅ Validação dos campos obrigatórios e CPF
  validateFields(): boolean {
    this.errors = {}; // Limpa os erros antes de validar

    if (!this.personData.name) this.errors.name = 'O nome é obrigatório!';
    if (!this.personData.email) this.errors.email = 'O e-mail é obrigatório!';
    if (!this.personData.password) this.errors.password = 'A senha é obrigatória!';
    if (!this.personData.phoneNumber) this.errors.phoneNumber = 'O telefone é obrigatório!';
    if (!this.personData.dateBirth) this.errors.dateBirth = 'A data de nascimento é obrigatória!';
    if (!this.personData.gender) this.errors.gender = 'O gênero é obrigatório!';
    if (!this.personData.rg) this.errors.rg = 'O RG é obrigatório!';
    if (!this.personData.civilState) this.errors.civilState = 'O estado civil é obrigatório!';
    if (!this.personData.role) this.errors.role = 'O cargo é obrigatório!';

    // Valida CPF
    if (!this.personData.cpf) {
      this.errors.cpf = 'O CPF é obrigatório!';
    } else if (!this.isValidCPF(this.personData.cpf)) {
      this.errors.cpf = 'O CPF informado é inválido!';
    }

    // Valida CPF do banco
    if (!this.personData.bankAccount.bankCpf) {
      this.errors.bankCpf = 'O CPF da conta bancária é obrigatório!';
    } else if (!this.isValidCPF(this.personData.bankAccount.bankCpf)) {
      this.errors.bankCpf = 'O CPF da conta bancária é inválido!';
    }

    if (!this.personData.bankAccount.privateKey) this.errors.privateKey = 'A chave privada é obrigatória!';
    if (!this.personData.bankAccount.ethAddress) this.errors.ethAddress = 'O endereço Ethereum é obrigatório!';

    return Object.keys(this.errors).length === 0; // Retorna true se não houver erros
  }

  onSubmit() {
    console.log('🔹 Validando dados antes do envio:', this.personData);

    if (!this.validateFields()) {
      return; // Interrompe o envio se houver erros
    }

    this.personService.register(this.personData).subscribe({
      next: () => {
        this.successMessage = '✅ Cadastro realizado com sucesso!';
      
      // Aguarda 3 segundos antes de redirecionar
      setTimeout(() => {
        this.successMessage = '';
        this.router.navigate(['/main']);
      }, 3000);
    },
      error: (err) => {
        console.error('❌ Erro ao cadastrar usuário:', err);
        alert('❌ Erro ao cadastrar usuário.');
      }
    });
  }
}

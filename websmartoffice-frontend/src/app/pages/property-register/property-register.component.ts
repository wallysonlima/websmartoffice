import { Component } from '@angular/core';
import { PropertyService } from '../../services/property.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-property-register',
  standalone: true,
  templateUrl: './property-register.component.html',
  styleUrls: ['./property-register.component.scss'],
  imports: [CommonModule, FormsModule]
})
export class PropertyRegisterComponent {
  propertyData: any = {
    personCpf: '',
    registerProperty: '',
    notarialDeed: '',
    price: '',
    size: '',
    address: ''
  };

  errors: any = {}; // ✅ Armazena mensagens de erro
  successMessage: string = '';

  constructor(private propertyService: PropertyService, private router: Router) {}

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

  // ✅ Valida os campos obrigatórios e CPF
  validateFields(): boolean {
    this.errors = {}; // Limpa os erros antes de validar

    if (!this.propertyData.personCpf) {
      this.errors.personCpf = 'O CPF do proprietário é obrigatório!';
    } else if (!this.isValidCPF(this.propertyData.personCpf)) {
      this.errors.personCpf = 'O CPF informado é inválido!';
    }

    if (!this.propertyData.registerProperty) {
      this.errors.registerProperty = 'O registro da propriedade é obrigatório!';
    }

    if (!this.propertyData.notarialDeed) {
      this.errors.notarialDeed = 'A escritura notarial é obrigatória!';
    }

    if (!this.propertyData.price || this.propertyData.price <= 0) {
      this.errors.price = 'O preço deve ser maior que zero!';
    }

    if (!this.propertyData.size) {
      this.errors.size = 'O tamanho da propriedade é obrigatório!';
    }

    // Validação do endereço
    if (!this.propertyData.address) {
      this.errors.streetName = 'O endereço é obrigatória!';
    }

    return Object.keys(this.errors).length === 0; // Retorna true se não houver erros
  }

  onSubmit() {
    console.log('🔹 Validando dados antes do envio:', this.propertyData);

    if (!this.validateFields()) {
      return; // Interrompe o envio se houver erros
    }

    this.propertyService.register(this.propertyData).subscribe({
      next: () => {
        this.successMessage = '✅ Cadastro realizado com sucesso!';
      
      // Aguarda 3 segundos antes de redirecionar
      setTimeout(() => {
        this.successMessage = '';
        this.router.navigate(['/main']);
      }, 3000);
    },
      error: (err) => {
        console.error('❌ Erro ao cadastrar propriedade:', err);
        alert('❌ Erro ao cadastrar a propriedade.');
      }
    });
  }
}

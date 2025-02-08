import { Component, OnInit } from '@angular/core';
import { ApiService } from '../../services/api.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css'],
})
export class AdminComponent implements OnInit {
  users: any[] = [];
  accounts: any[] = [];
  transactions: any[] = [];
  selectedUser: any = null;
  newBalance: number = 0;
  successMessage: string = '';
  errorMessage: string = '';
  selectedFilter: string = 'ALL';
  filteredTransactions: any[] = [];
  sortOrder: string = 'desc';

  constructor(private apiService: ApiService) {}

  ngOnInit() {
    this.fetchUsers();
    this.fetchAccounts();
    this.fetchTransactions();
   
  }



  fetchUsers() {
    this.apiService.getAllUsers().subscribe({
      next: (data) => (this.users = data),
      error: () => (this.errorMessage = 'Error fetching users!'),
    });
  }

  fetchAccounts() {
    this.apiService.getAllAccountsAdmin().subscribe({
      next: (data) => (this.accounts = data),
      error: () => (this.errorMessage = 'Error fetching accounts!'),
    });
  }

  fetchTransactions() {
    this.apiService.getAllTransactionsAdmin().subscribe({
      next: (data) => (this.transactions = data),
      error: () => (this.errorMessage = 'Error fetching transactions!'),
    });
  }

  selectUser(user: any) {
    this.selectedUser = { ...user };
    console.log('Selected user:',this.selectedUser);
    this.successMessage = '';
    this.errorMessage = '';
  }

  updateUser() {
    if (this.selectedUser) {
      console.log('Updating User:', this.selectedUser); // Debugging log
      if (!this.selectedUser.id) {
        this.errorMessage = '❌ Error: User ID is missing!';
        return;
      }
  
      this.apiService.updateUser(this.selectedUser.id, this.selectedUser).subscribe({
        next: () => {
          this.successMessage = '✅ User updated successfully!';
          this.fetchUsers();
          this.selectedUser = null; // Clear selection
        },
        error: () => (this.errorMessage = '❌ Error updating user!'),
      });
      setTimeout(() => {
        this.successMessage = '';
        this.errorMessage = '';
      }, 5000);
      
    }
  }

  updateBalance(account: any) {
    if (account.newBalance > 0) {
      this.apiService.updateAccountBalance(account.id, account.newBalance).subscribe({
        next: () => {
          this.successMessage = '✅ Balance updated successfully!';
          this.fetchAccounts();
          account.newBalance = null; // Reset input field
        },
        error: () => (this.errorMessage = '❌ Error updating balance!'),
      });
    }
    setTimeout(() => {
      this.successMessage = '';
      this.errorMessage = '';
    }, 5000);
    
  }

  // deleteUser(userId: number) {
  //   if (confirm('Are you sure you want to delete this user?')) {
  //     this.apiService.deleteUser(userId).subscribe({
  //       next: () => {
  //         this.successMessage = '✅ User deleted successfully!';
  //         this.fetchUsers();
  //       },
  //       error: () => (this.errorMessage = '❌ Error deleting user!'),
  //     });
  //   }
  // }
  deleteUser(userId: number) {
    this.errorMessage = '';
    this.successMessage = '';
  
    this.showDeleteConfirmation(userId); // Open delete confirmation modal
  }
  
  // ✅ Show delete confirmation modal
  showDeleteConfirmation(userId: number) {
    const confirmDelete = window.confirm('Are you sure you want to delete this user?');
    
    if (confirmDelete) {
      this.apiService.deleteUser(userId).subscribe({
        next: () => {
          this.successMessage = '✅ User deleted successfully!';
          this.fetchUsers(); // Refresh user list
        },
        error: () => {
          this.errorMessage = '❌ Error deleting user!';
        }
      });
    }
    setTimeout(() => {
      this.successMessage = '';
      this.errorMessage = '';
    }, 5000);
    
  }

  filterTransactions() {
    this.applyFiltersAndSorting();
  }
  applyFiltersAndSorting() {
    let filtered = this.transactions.filter((t) =>
      this.selectedFilter === 'ALL' ? true : t.transactionType === this.selectedFilter
    );

    this.filteredTransactions = filtered.sort((a, b) => {
      return this.sortOrder === 'desc'
        ? new Date(b.transactionDateTime).getTime() - new Date(a.transactionDateTime).getTime()
        : new Date(a.transactionDateTime).getTime() - new Date(b.transactionDateTime).getTime();
    });
  }
  toggleSortOrder() {
    this.sortOrder = this.sortOrder === 'desc' ? 'asc' : 'desc';
    this.applyFiltersAndSorting();
  }
  
}
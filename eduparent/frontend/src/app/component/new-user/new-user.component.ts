import {Component, Input, OnInit} from '@angular/core';
import {RoleService} from "../../service/role/role.service";
import {Role} from "../../model/role";
import {Ng4LoadingSpinnerService} from "ng4-loading-spinner";
import {User} from "../../model/user";
import {AccountService} from "../../service/account/account.service";
import {UserService} from "../../service/user/user.service";
import {Account} from "../../model/account";
import {UserStorageService} from "../../service/user/user-storage.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-new-user',
  templateUrl: './new-user.component.html',
  styleUrls: ['./new-user.component.css']
})
export class NewUserComponent implements OnInit {

  @Input()
  public modalRef;
  public roles: Role[];
  public user: User;
  public account: Account;
  public errorLogin: boolean = false;
  public errorEmail: boolean = false;

  constructor(private roleService: RoleService, private loadingService: Ng4LoadingSpinnerService, private userService: UserService,
              private accountService: AccountService, private fb: FormBuilder) {
  }

  ngOnInit() {
    this.user = new User();
    this.account = new Account();
    this.loadRoles();
  }


  private loadRoles(): void {
    this.loadingService.show();
    this.roleService.getRoles().subscribe(
      role => {
        this.roles = role as Role[];
        this.user.role = this.roles[3];
        this.loadingService.hide();
      }
    );
  }

  public closeModal(): void {
    this.modalRef.hide();
  }

  public addUser(): void {
    this.loadingService.show()
    this.userService.getUserByLogin(this.user.login).subscribe(
      user => {
        if (user) {
          this.errorLogin = true;
        } else {
          this.accountService.getAccountByEmail(this.account.email).subscribe(
            account =>{
              if(account){
                this.errorEmail = true;
              }else {
                this.saveUser();
              }
            }
          )
        }
      }
    )
  }

  public setErrorLogin(): void {
    this.errorLogin = false;
  }

  public setErrorEmail(): void {
    this.errorEmail = false;
  }

  saveUser(): void {
    this.userService.saveUser(this.user).subscribe(
      (user) => {
        if(user) {
          this.getUserByLogin();
        }else{
          this.errorLogin = true;
        }
      }
    )
  }

  private getUserByLogin() {
    this.userService.getUserByLogin(this.user.login).subscribe(
      user => {
        this.account.userId = user.id;
        this.addAccount();
      }
    )
  }

  private addAccount(): void {
    this.accountService.saveAccount(this.account).subscribe(
      () => {
        this.refreshUser();
        this.loadingService.hide();
        this.modalRef.hide();
      }
    )
  }

  private refreshUser(): void {
    this.account = new Account();
    this.user = new User();
  }
}

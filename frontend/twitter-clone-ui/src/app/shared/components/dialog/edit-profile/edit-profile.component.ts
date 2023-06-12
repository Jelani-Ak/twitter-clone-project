import { Component, Inject, OnInit } from '@angular/core';
import {
  AbstractControlOptions,
  FormBuilder,
  FormGroup,
  Validators,
} from '@angular/forms';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { StorageService } from 'src/app/core/services/storage/storage.service';
import { UserProfileService } from 'src/app/core/services/user-profile/user-profile.service';
import { UserService } from 'src/app/core/services/user/user.service';
import { UserProfileDTO } from 'src/app/shared/models/user';
import { ConfirmationDialogComponent } from '../confirmation-dialog/confirmation-dialog.component';

@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrls: ['./edit-profile.component.css'],
})
export class EditProfileComponent implements OnInit {
  public get currentUser(): any {
    return this.storageService.getUser();
  }

  // Properties must be kept in the same order as listed in the User.ts model
  public profileForm: FormGroup = this.formBuilder.group({
    username: ['', [Validators.required]],
    password: ['', []],
    email: ['', [Validators.required, Validators.email]],
    displayName: ['', []],
    userHandleName: ['', []],
    bioAboutText: ['', []],
    bioLocation: ['', []],
    bioExternalLink: ['', []],
  } as AbstractControlOptions);

  public dialogOpen: boolean = false;
  public showPassword: boolean = false;
  public inputType = 'password';

  constructor(
    private dialog: MatDialog,
    private formBuilder: FormBuilder,
    private userService: UserService,
    private storageService: StorageService,
    private userProfileService: UserProfileService
  ) {}

  public ngOnInit(): void {
    this.initialiseProfileData();
  }

  public updateProfile() {
    const passwordChanged = this.profileForm.get('password')?.value.length > 0;
    if (passwordChanged) {
      const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
        id: 'confirm-password',
        data: {
          title: `Password Changed`,
          message: `Are you sure you want to change your password?`,
          dialogOpen: (this.dialogOpen = true),
        },
        width: '400px',
      });

      dialogRef.afterClosed().subscribe((data: string) => {
        (data === 'yes') ? this.setProfile() : null;
        this.closeDialog();
      });
    } else {
      this.setProfile();
      this.closeDialog();
    }
  }

  private closeDialog() {
    this.dialogOpen = false;
    this.dialog.closeAll();
  }

  public setProfile() {
    const userProfile: UserProfileDTO = {
      userId: this.currentUser.id,
      username: this.profileForm.value.username,
      password: this.profileForm.value.password,
      email: this.profileForm.value.email,
      displayName: this.profileForm.value.displayName,
      userHandleName: this.profileForm.value.userHandleName,
      bioAboutText: this.profileForm.value.bioAboutText,
      bioLocation: this.profileForm.value.bioLocation,
      bioExternalLink: this.profileForm.value.bioExternalLink,
    };

    this.userProfileService.updateProfile(userProfile).subscribe({
      complete: () => {
        console.log('Profile updated');
      },
      error: (error) => {
        console.log('Failed to update profile', error);
      },
    });
  }

  public toggleShowPassword() {
    this.showPassword = !this.showPassword;

    if (this.showPassword == true) {
      this.inputType = 'text';
    } else {
      this.inputType = 'password';
    }
  }

  private initialiseProfileData() {
    this.userService.findByUserId(this.currentUser.id).subscribe({
      next: (user) => {
        let j = 0;

        Object.keys(user).forEach((userProperty) => {
          const profileFormProperty = Object.keys(this.profileForm.value)[j];
          if (userProperty == 'password' || profileFormProperty == 'password') {
            j++;
            return;
          }

          if (userProperty != profileFormProperty) {
            return;
          }

          this.profileForm.get(profileFormProperty)?.setValue(Object.values(user)[++j]);
        });
      },
      complete: () => {
        console.log('Retrieved profile information');
      },
      error: (error) => {
        console.error('Failed to rerieve profile infromation', error);
      },
    });
  }
}

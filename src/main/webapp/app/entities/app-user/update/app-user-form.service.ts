import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IAppUser, NewAppUser } from '../app-user.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { idUser: unknown }> = Partial<Omit<T, 'idUser'>> & { idUser: T['idUser'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAppUser for edit and NewAppUserFormGroupInput for create.
 */
type AppUserFormGroupInput = IAppUser | PartialWithRequiredKeyOf<NewAppUser>;

type AppUserFormDefaults = Pick<NewAppUser, 'idUser'>;

type AppUserFormGroupContent = {
  idUser: FormControl<IAppUser['idUser'] | NewAppUser['idUser']>;
  email: FormControl<IAppUser['email']>;
  password: FormControl<IAppUser['password']>;
  balance: FormControl<IAppUser['balance']>;
  lastName: FormControl<IAppUser['lastName']>;
  firstName: FormControl<IAppUser['firstName']>;
  user: FormControl<IAppUser['user']>;
  idUserType: FormControl<IAppUser['idUserType']>;
};

export type AppUserFormGroup = FormGroup<AppUserFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AppUserFormService {
  createAppUserFormGroup(appUser: AppUserFormGroupInput = { idUser: null }): AppUserFormGroup {
    const appUserRawValue = {
      ...this.getFormDefaults(),
      ...appUser,
    };
    return new FormGroup<AppUserFormGroupContent>({
      idUser: new FormControl(
        { value: appUserRawValue.idUser, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      email: new FormControl(appUserRawValue.email, {
        validators: [Validators.required],
      }),
      password: new FormControl(appUserRawValue.password, {
        validators: [Validators.required],
      }),
      balance: new FormControl(appUserRawValue.balance),
      lastName: new FormControl(appUserRawValue.lastName, {
        validators: [Validators.required],
      }),
      firstName: new FormControl(appUserRawValue.firstName, {
        validators: [Validators.required],
      }),
      user: new FormControl(appUserRawValue.user),
      idUserType: new FormControl(appUserRawValue.idUserType),
    });
  }

  getAppUser(form: AppUserFormGroup): IAppUser | NewAppUser {
    return form.getRawValue() as IAppUser | NewAppUser;
  }

  resetForm(form: AppUserFormGroup, appUser: AppUserFormGroupInput): void {
    const appUserRawValue = { ...this.getFormDefaults(), ...appUser };
    form.reset(
      {
        ...appUserRawValue,
        idUser: { value: appUserRawValue.idUser, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): AppUserFormDefaults {
    return {
      idUser: null,
    };
  }
}

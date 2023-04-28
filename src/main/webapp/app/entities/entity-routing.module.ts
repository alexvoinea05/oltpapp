import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'license',
        data: { pageTitle: 'Licenses' },
        loadChildren: () => import('./license/license.module').then(m => m.LicenseModule),
      },
      {
        path: 'company',
        data: { pageTitle: 'Companies' },
        loadChildren: () => import('./company/company.module').then(m => m.CompanyModule),
      },
      {
        path: 'company-x-license',
        data: { pageTitle: 'CompanyXLicenses' },
        loadChildren: () => import('./company-x-license/company-x-license.module').then(m => m.CompanyXLicenseModule),
      },
      {
        path: 'journey-status',
        data: { pageTitle: 'JourneyStatuses' },
        loadChildren: () => import('./journey-status/journey-status.module').then(m => m.JourneyStatusModule),
      },
      {
        path: 'incident-status',
        data: { pageTitle: 'IncidentStatuses' },
        loadChildren: () => import('./incident-status/incident-status.module').then(m => m.IncidentStatusModule),
      },
      {
        path: 'user-type',
        data: { pageTitle: 'UserTypes' },
        loadChildren: () => import('./user-type/user-type.module').then(m => m.UserTypeModule),
      },
      {
        path: 'train-type',
        data: { pageTitle: 'TrainTypes' },
        loadChildren: () => import('./train-type/train-type.module').then(m => m.TrainTypeModule),
      },
      {
        path: 'fuel-type',
        data: { pageTitle: 'FuelTypes' },
        loadChildren: () => import('./fuel-type/fuel-type.module').then(m => m.FuelTypeModule),
      },
      {
        path: 'railway-type',
        data: { pageTitle: 'RailwayTypes' },
        loadChildren: () => import('./railway-type/railway-type.module').then(m => m.RailwayTypeModule),
      },
      {
        path: 'district',
        data: { pageTitle: 'Districts' },
        loadChildren: () => import('./district/district.module').then(m => m.DistrictModule),
      },
      {
        path: 'city',
        data: { pageTitle: 'Cities' },
        loadChildren: () => import('./city/city.module').then(m => m.CityModule),
      },
      {
        path: 'address',
        data: { pageTitle: 'Addresses' },
        loadChildren: () => import('./address/address.module').then(m => m.AddressModule),
      },
      {
        path: 'railway-station',
        data: { pageTitle: 'RailwayStations' },
        loadChildren: () => import('./railway-station/railway-station.module').then(m => m.RailwayStationModule),
      },
      {
        path: 'train',
        data: { pageTitle: 'Trains' },
        loadChildren: () => import('./train/train.module').then(m => m.TrainModule),
      },
      {
        path: 'app-user',
        data: { pageTitle: 'AppUsers' },
        loadChildren: () => import('./app-user/app-user.module').then(m => m.AppUserModule),
      },
      {
        path: 'journey',
        data: { pageTitle: 'Journeys' },
        loadChildren: () => import('./journey/journey.module').then(m => m.JourneyModule),
      },
      {
        path: 'incident',
        data: { pageTitle: 'Incidents' },
        loadChildren: () => import('./incident/incident.module').then(m => m.IncidentModule),
      },
      {
        path: 'ticket',
        data: { pageTitle: 'Tickets' },
        loadChildren: () => import('./ticket/ticket.module').then(m => m.TicketModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}

import { NgModule } from "@angular/core";
import { MatToolbarModule } from '@angular/material/toolbar'
import { MatButtonModule } from '@angular/material/button'
import { MatIconModule } from '@angular/material/icon'
import { MatInputModule } from '@angular/material/input'
import { MatFormFieldModule } from '@angular/material/form-field'
import { MatSelectModule} from '@angular/material/select'
import { MatSidenavModule } from '@angular/material/sidenav'
import { MatMenuModule } from '@angular/material/menu'
import { MatListModule } from '@angular/material/list'
import { MatCardModule } from '@angular/material/card'
import { MatDatepickerModule } from '@angular/material/datepicker'
import { MatNativeDateModule } from '@angular/material/core';
import { MatMomentDateModule } from '@angular/material-moment-adapter';
import { MatTableModule } from '@angular/material/table';

import { MatPaginatorModule } from '@angular/material/paginator';


const matModules: any[] = [
  MatToolbarModule, MatButtonModule, MatListModule,
  MatIconModule, MatInputModule, MatFormFieldModule,
  MatSelectModule, MatSidenavModule, MatMenuModule, 
  MatCardModule, MatDatepickerModule, MatNativeDateModule,
  MatMomentDateModule, MatTableModule, MatPaginatorModule

]

@NgModule({
  imports: matModules,
  exports: matModules
})
export class MaterialModule {}
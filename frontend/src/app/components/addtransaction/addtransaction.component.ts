import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Transaction } from 'src/app/_models/transaction.model';
import { StorageService } from 'src/app/_services/storage.service';
import { TransactionService } from 'src/app/_services/transaction.service';

@Component({
  selector: 'app-addtransaction',
  templateUrl: './addtransaction.component.html',
  styleUrls: ['./addtransaction.component.css']
})
export class AddtransactionComponent implements OnInit {

  form!: FormGroup
  username!: string
  isLoggedIn = false;
  isSubmitted = false;

  @ViewChild('toUpload')
  toUpload!: ElementRef  
  
  constructor(private fb: FormBuilder, private txService: TransactionService, private storageService: StorageService) { }

  ngOnInit(): void {
    this.form = this.createForm()
    this.isLoggedIn = this.storageService.isLoggedIn();
    if (this.isLoggedIn) {
      const user = this.storageService.getUser();
      this.username = user.username;
    }
    this.form = this.createForm()
  }

  createForm(){
    return this.fb.group({
      category: this.fb.control<string>('', [Validators.required]),
      description: this.fb.control<string>(''),
      picture: this.fb.control<any>(''),
      amount: this.fb.control<number>(0,[ Validators.required ]),
      date: [new Date(), Validators.required]
    })
    
  }

  submitTransaction(){
    console.info('>>>submit: ', this.form.value)
    const myFile = this.toUpload.nativeElement.files[0]
    let tx: Transaction = this.form.value as Transaction
    console.log('>>tx to upload: ', tx)
    this.txService.uploadTransaction(tx, myFile)
    this.isSubmitted = true;
  }

  reloadPage(): void {
    window.location.reload();
  }
 

}

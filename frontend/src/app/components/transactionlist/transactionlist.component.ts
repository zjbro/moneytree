import { AfterViewInit, Component, ElementRef, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { Transaction } from 'src/app/_models/transaction.model';
import { TransactionService } from 'src/app/_services/transaction.service';
import { MatTableDataSource } from '@angular/material/table';
import { ModalService } from 'src/app/_modals/modal.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { StorageService } from 'src/app/_services/storage.service';


@Component({
  selector: 'app-transactionlist',
  templateUrl: './transactionlist.component.html',
  styleUrls: ['./transactionlist.component.css']
})
export class TransactionlistComponent implements OnInit, OnDestroy {

  @ViewChild(MatSort) 
  sort!: MatSort;

  @ViewChild(MatPaginator)
  paginator!: MatPaginator;
  
  @ViewChild('toUpload')
  toUpload!: ElementRef 

  startingIndexOfPage!: number;
  endingIndexOfPage!: number
  clickedItemId!: number;
  count!: number;
  innerIndexCalculated!: number;
  
  
  tableData: any;
  columns: string[] = ['date', 'category', 'description', 'picture', 'amount', 'actions'];

  form!: FormGroup
  isLoggedIn = false;
  isSubmitted = false;

  username = this.ar.snapshot.params['username']
  transactions: Transaction[] = []
  sub$!: Subscription
  dataSource!: MatTableDataSource<Transaction>;
  bodyText!: string;
  selected = 'food'


  constructor(private transactionService: TransactionService, 
      private ar:ActivatedRoute, 
      private modalService: ModalService,
      private fb: FormBuilder,
      private storageService: StorageService
      ){}

  ngOnInit(): void {
    this.bodyText = 'This text can be updated in modal 1';
    this.sub$ = this.transactionService.OnNewTransaction.subscribe()
    this.transactionService.getTransactionList(this.username)
    .then(result => {
      console.log(">>>>Transactions from server: ", result)
      this.transactions = result
      this.tableData = this.transactions
      this.dataSource = new MatTableDataSource(this.transactions);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    }).catch(error =>{
      console.error('>>>>Error: ', error)
    })
    this.isLoggedIn = this.storageService.isLoggedIn();
    if (this.isLoggedIn) {
      const user = this.storageService.getUser();
    }
    this.form = this.createForm()
  }

  ngOnDestroy(): void {
      this.sub$.unsubscribe()
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  removeTransaction(i: number){
    this.innerIndexCalculated = this.startingIndexOfPage + i
    let txId = this.transactions[this.innerIndexCalculated].transactionId
    this.transactionService.deleteTransaction(txId)
    this.reloadPage();
  }

  reloadPage(): void {
    window.location.reload();
  }

  onContentChange(event: any) {
    this.getCurrentIndex()
  }

  getCurrentIndex() {
    this.startingIndexOfPage = this.paginator.pageIndex * this.paginator.pageSize;
    this.endingIndexOfPage = (this.paginator.pageIndex * this.paginator.pageSize) + this.paginator.pageSize;
  }

  returnCurrentIndex(i: number) {
    this.startingIndexOfPage = this.paginator.pageIndex * this.paginator.pageSize;
    return this.startingIndexOfPage + i;
  }

  openModal(id: number) {
    this.modalService.open(id);
}

  closeModal(id: number) {
    this.modalService.close(id);
  }

  createForm(){
    return this.fb.group({
      category: this.fb.control<string>('', [Validators.required]),
      description: this.fb.control<string>(''),
      picture: this.fb.control<any>(''),
      amount: this.fb.control<number>(0,[ Validators.required ]),
      date: [new Date(), Validators.required],
      username: this.username,
    })

  }

  updateTransaction(i: number){
    console.info('>>>submit: ', this.form.value)
    const myFile = this.toUpload.nativeElement.files[0]
    let tx: Transaction = this.form.value as Transaction
    this.innerIndexCalculated = this.startingIndexOfPage + i
    tx.transactionId = this.transactions[this.innerIndexCalculated].transactionId
    console.log('>>tx to upload: ', tx)
    this.transactionService.updateTransaction(tx, myFile)
    this.isSubmitted = true;
    this.reloadPage();
  }

  

}

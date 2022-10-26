import { AfterViewInit, Component, ElementRef, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { Transaction } from 'src/app/_models/transaction.model';
import { TransactionService } from 'src/app/_services/transaction.service';
import { MatTableDataSource } from '@angular/material/table';

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
  tableData: any;
  columns: string[] = ['date', 'category', 'description', 'picture', 'amount', 'actions'];

  username = this.ar.snapshot.params['username']
  transactions: Transaction[] = []
  sub$!: Subscription
  dataSource!: MatTableDataSource<Transaction>;


  constructor(private transactionService: TransactionService, private ar:ActivatedRoute) {}

  ngOnInit(): void {
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
    //@ts-ignore
    const transactionId = this.transactions[i].transactionId
    this.transactionService.deleteTransaction(transactionId)
    this.reloadPage();
  }

  reloadPage(): void {
    window.location.reload();
  }
}

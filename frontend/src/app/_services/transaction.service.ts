import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import * as moment from "moment";
import { firstValueFrom, Subject } from "rxjs";
import { Transaction } from "../_models/transaction.model";



@Injectable()
export class TransactionService{   
    
    OnNewTransaction = new Subject<String>()
    
    constructor(private http: HttpClient){}



    uploadTransaction(tx: Transaction, file: File | Blob){

        const data = new FormData()
        data.set('category', tx.category)
        data.set('description', tx.description)
        data.set('picture', file)
        data.set('amount', tx.amount)
        data.set('date',moment(tx.date).format('YYYY-MM-DD HH:mm'))
        data.set('username', tx.username)
        return firstValueFrom(
            this.http.post<any>('/api/addTransaction', data)
        )
    }
    
    getTransactionList(username: string): Promise<Transaction[]>{
        return firstValueFrom(
            this.http.get<Transaction[]>(`/api/getTransactions/${username}/all`)
            )
    }

    deleteTransaction(transactionId: string){
        return firstValueFrom(
            this.http.delete<any>(`/api/deleteTransaction?transactionId=${transactionId}`)
        )
    }
    
    updateTransaction(tx: Transaction, file: File | Blob){

        const data = new FormData()
        data.set('transactionId', tx.transactionId)
        data.set('category', tx.category)
        data.set('description', tx.description)
        data.set('picture', file)
        data.set('amount', tx.amount)
        data.set('date',moment(tx.date).format('YYYY-MM-DD HH:mm'))
        data.set('username', tx.username)
        return firstValueFrom(
            this.http.post<any>('/api/updateTransaction', data)
        )
    }

    deleteAccount(username: string){
        const data = new FormData()
        data.set('username', username)

        return firstValueFrom(
            this.http.post<any>('/api/deleteAccount', data)
        )
    }

    getRandomCatFact(){
        return firstValueFrom(
            this.http.get<any>('/api/catfact')
        )
    }



   
}
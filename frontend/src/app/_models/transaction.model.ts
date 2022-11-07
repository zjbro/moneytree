export interface Transaction {
    transactionId: string,
    category: string,
    description: string,
    picture: Blob,
    amount: string,
    date: string,
    username: string
}
import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class ModalService {
    private modals: any[] = [];

    add(modal: any) {
        // add modal to array of active modals
        this.modals.push(modal);
    }

    remove(id: string) {
        // remove modal from array of active modals
        this.modals = this.modals.filter(x => x.id !== id);
    }

    open(id: number) {
        // open modal specified by id
        const modal = this.modals[id];
        console.log(">>>>modals: ",this.modals)
        console.log(">>>>>id of modal: ", id)
        console.log(">>>>modal: ",modal)
        modal.open();
    }

    close(id: number) {
        // close modal specified by id
        const modal = this.modals[id];
        modal.close();
    }
}
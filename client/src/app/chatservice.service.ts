import { Injectable } from '@angular/core';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client'; 
import { BehaviorSubject } from 'rxjs';
import { NewMessage } from './newmessage.model';
@Injectable({
  providedIn: 'root'
})

export class ChatService {  

  public messages:string[]=[]
  private stompClient:any = null 
  private connected=false
  public newmessage: BehaviorSubject<NewMessage> = new BehaviorSubject<NewMessage> ({ 
    latestmessage : '',
    sendername:''

}) 
   


  connect(username:string){
    if(this.connected===false){  
    const server = new SockJS('http://localhost:8080/chat') 
    this.stompClient = Stomp.over(server)    
    const that = this
    that.stompClient.connect({},function(frame:any){ 
      that.connected = true;
      that.stompClient.subscribe('/user/'+username+'/private',function(message:any){  
        if(message.body){ 
         that.showMessage(JSON.parse(message.body))
        }
    },{})
   })
  }
  }

  disconnect() {
    if (this.stompClient != null) {
      this.stompClient.disconnect();
    }
  }
  sendPrivateMessage(message:any){   
    this.stompClient.send('/app/message',{},JSON.stringify(message))
  }

  showMessage(message:any){
     this.newmessage.next({latestmessage:message.message, sendername:message.senderName})
  }
} 


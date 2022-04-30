import { Component, OnInit } from '@angular/core';
import { UserData } from './userdata.model';
import { ChatService } from './chatservice.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit {
  userdata: UserData;
  title = 'docnowclient';
  username: string;
  receivername: string;
  senderMessages: string[] = [];
  receiverMessages: string[] = [];
  senderMessagesMap: Map<string, string[]> = new Map<string, string[]>();
  receiverMessagesMap: Map<string, string[]> = new Map<string, string[]>();
  message: string;
  messagesMap: Map<string, Map<string, string[]>> = new Map<
    string,
    Map<string, string[]>
  >();

  constructor(private chatService: ChatService) {}

  ngOnInit(): void {
    this.chatService.newmessage.subscribe((res) => {
      console.log(res);
      if (res.latestmessage !== '') {
        if (this.receiverMessagesMap.get(res.sendername)) {
          this.receiverMessagesMap.get(res.sendername).push(res.latestmessage);
          console.log('receivermsgsmap ' + this.receiverMessagesMap);
        } else {
          this.receiverMessages.push(res.latestmessage);
          console.log('receivermsgs ' + this.receiverMessages);
          this.receiverMessagesMap.set(res.sendername, this.receiverMessages);
          this.receiverMessages = [];
        }
        this.messagesMap.set(this.username, this.receiverMessagesMap);
        console.log(
          'receivermessagesmap ' + this.receiverMessagesMap.get(res.sendername)
        );
        console.log(this.messagesMap);
      }
    });
  }
  connect() {
    this.senderMessagesMap.clear();
    this.receiverMessagesMap.clear();
    this.chatService.connect(this.username);
  }

  disconnect() {
    this.chatService.disconnect();
  }
  sendName() {
    let message = {
      senderName: this.username,
      receiverName: this.receivername,
      message: this.message,
    };

    this.chatService.sendPrivateMessage(message);
    if (this.senderMessagesMap.get(this.receivername)) {
      this.senderMessagesMap.get(this.receivername).push(message.message);
    } else {
      this.senderMessages.push(message.message);
      this.senderMessagesMap.set(this.receivername, this.senderMessages);
      this.senderMessages = [];
    }
    this.messagesMap.set(this.username, this.senderMessagesMap);
    console.log(this.messagesMap);
  }
  getNewName(e: Event) {
    this.senderMessages = [];
    this.receiverMessages = [];
  }
}

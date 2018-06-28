# P2Pcluster
A dynamic scalable configurable Server for global IoT and path routing 

Let us start from middle of technical stuffs, hoping you already know what you are dealing with.
Here, we are talking about Global M2M mesh, where any machine can send message to any machine connected to internet (They call it IoT).

Before you move forward, let me give the example that you need to start “doing” than “reading”

1. Just open putty (Later on you can use any client) and set followings and save the profile (you may need to type again and again) and then Open connection.
Hostname: p2pcluster.org
port: 80
Connection Type: Raw

2. Now, very fast (with in a minute), fire this command in putty console:
Example: TCP:ID:11FE313037C5327CD35BAC6C8D66867F:123
You must get a return statement something like [OK:123]

3. Now open another putty console with same credentials as above and fire command with different ID. Something like
TCP:ID:11FE313037C5327CD35BAC6C8D66867F:1234
You must get the same reply [OK:1234]

4. Great. Now fire bellow command in any putty console (say, at console id-123)
TCP:PUSH:11FE313037C5327CD35BAC6C8D66867F:RID:1234:Hello1234

5. The “Hello1234” should be sent to other console (with ID-1234).
Interesting?
6 Now open another putty console with ID 12345.
7. In any Putty console fire below command:
TCP:PUSH:11FE313037C5327CD35BAC6C8D66867F:FRIENDS:Hello ALL
8. You should get “Hello ALL” to all other putty consoles!

Seems interesting? If yes, read further, explore more and put comments or help me to find bugs.
Follow the API to get your own private keys and start using it.

Some preamble:

So, what are the common problems in traditional M2M scenario?


High Level API and custom client stack (MQTT etc?):
I know, you already know Google, Apple, Parse are there giving solution and you are finding ways how to use there API to use in your tiny hardware! Frankly speaking, Its too small tiny hardware and cant afford the high level APIs that those giants are providing for Tablet, mobile, desktops. Why those people cant give simple raw low level API too?? Abstraction? Yes, they have their own business model and security. But we need a low level raw TCP API, and hence, P2Pcluster is here. It is designed to support ALL KIND of IoT device, starting from a core running raw C or C++ or JAVA or NodeJS or Python…whatever hell running in any OS Android or Windows or Linux or NO-OS. That we intended to do.

ADDRESS:
An IoT device needs at-least a remote IP and a Port to communicate (See OSI model and TCP communication). The IP, the address ! The most precious thing. How will you make your IP static? Or really can you do it? Here, its not a client server communication, rather a client-client communication where all clients are a machine, or individual IoT node or a mobile phone or a PC sitting in deep corner of a LAN or WAN. So, all addresses are virtual and cant assure its static property. How do you handle it? We do some arrangement for case to case basis by fixing stuffs (address as static). I am sure, you always dream a solution like Whatsapp! Anyone can send any message to anyone, anywhere, and you must be waiting when Zuckerberg will release an API extending its XMPP service to allow to connect Whatsapp to your IoT device. Well, I am not his friend, or never will be :P, but I can realize the situation as I am passing with same, so, I made the P2Pcluster that will relay one machine’s message to another machine independent of its depth of address (local, virtual whatever) and all will be identified by its unique ID, that will be assigned by you (Like your mail-ID, phone no etc. No need to depend on IP4 or IP6 address, I am dealing it at my side. All machines needs to connect with P2Pcluster at port 80 and then do send your message to destination indicating remote ID.

ID:
Well, I have removed dependency on dynamic and virtual address and given an ID decided by you. But again, that ID may also not be the best. Did you deploy any IoT device? If yes, you must realize the problems with ID itself. Take an use case, assume you have three devices having ID as 1,2 and 3. And assume you have a logic implemented in ID-1 device, “If I am ON, turn ID-2 OFF”. Very well. Now realize the situation, Device ID-2 became damage and eventually you replaced it by new device (do you give warranty 😛 ?) having ID-4. Oops! Now the old logic “If I am ON, turn ID-2 OFF” will fail ! You need to update the logic as “If I am ON, turn ID-4 OFF”, by updating device ID-1 firmware. Great, I know, till now you are doing so.
Here, is a new concept, that will remove the concept of ID, rater, it is rationalized as various synonymous nomenclature as “FRIEND”, “CHILD” where the device will be classified by a class, not by ID. So, how will be logic? The logic to be implemented as “If I am ON, turn MY-FRIEND OFF” and in P2PCluster server, define MY-FRIEND as what-ever device-ID you want to link with ID-2 or ID-4, remotely without actually updating firmware of Device ID-1. Does it make sense? It is there in P2PCluster.

And here, a new concept has been added in P2PCluster that will help the IoT life much easier. A concept of Grouping has been added. There are two kinds of group- a) Hierarchical Group or Tree and b) Non-Hierarchical or Mesh

What is Group?
Group is a skeleton that stored in server defined and designed by user. User may define and create its own group providing respective Device-ID. When, that particular device is Up (Connected to Push Server), its position will automatically gathered.
a) Hierarchical Tree Group
User can define any node as ROOT (if not defined anything, by default all nodes or devices are ROOT). And can create the child and sub-child and further more. There are no limitation of no of nodes or devices under any node. All nodes at same level are called FRIEND (all Roots are friends, all child nodes under same parent are friends). All sub-nodes under a node are called CHILDS. CHILDS of a node is a different group than CHILDS of an another node. ALLCHILDS is a group that defines all the child and grand-child and grand-grand-childs….so on.
But, what is the hell meaning of this grouping?
Well, a node can send message to any group by simply sending a SINGLE message like:
TCP:PUSH:{KEY}:CHILDS:{DATA}

b)Non-Hierarchical Mesh Group
Non-Hierarchical mesh has its own essence and may desired in many situation than Tree architecture. I personally believe, Mesh can replace and remove the Tree, however, as the TREE concept evolved first and working fine, I am intending to keep it for a while.
Let us assume a real scenario test case: “Turn off all lights in Bed-room” or “Turn off all Garden Lights” or “Turn off all high power Lights” (in Night :P). Interestingly, here we are talking a group of devices and there may be multiple common (not all) devices in various group. So, in practical scenario, a device may be part of multiple GROUP. Hence, the TREE architecture may not suitable for messaging. So, the inclusion of MESH. Here, we can create any numbers of independent group under a single KEY.
How to create MESH group?
I have introducing a new entity called ROUTER that is a completely logical entity which resides in Server. User may configure it and connect any numbers of device with its ID. When any message is thrown targeting that specific ROUTER, the message will be broadcasted all the other device nodes.
Just create ROUTER, and connect device-ID with it using API at section 2.2
Then send your Message using simple TCP API like:
TCP:PUSH:{KEY}:RO:{INTERNAL_ROUTER_ID}:{DATA}

SECURITY:
Replay Attack:
First think about a Replay Attack. The “command” or “data” packet can be “copied” or “cloned”. And those cloned data may be replayed later on to regenerate same Result.
The method described below eliminates the possibility of Replay Attack, completely. Here, we hall be generating a session key for each client. And those session keys are based on a Random number sent by Server which can not be altered by client (attacker). Hence, although, data may be copied or same, but the session-Key will be different for all clients or session.

Let me use the very basic concept of Mutual-Authentication (I-Auth+E-Auth), by which, the actual KEY will be never exposed over the network and always we shall be using a derived key or session key. So, the KEY will be dynamic for every session, every device and all are derived from same key. Well, real KEY can not be mathematically derived from derived-key.

Let us see, how it is being done:

Device Verification: The client will be verified by server
1. You have the KEY, let us call it KEYSEC. Keep it secret. Only server knows it and you (IoT device) will be knowing it. It will never come out-side device at plain-text.
2. At connection initiation with PushServer, Server will throw the Random Number KEYRND
3. Hash after Concatenation KEYSEC and KEYRND. It will generate Session key- KEYSES . Use the session key for rest of all commands during the session.
Interestingly, all the client will get different Random number and the Session Key(KEYSES) will be different. So, even if the KEYSES is in plain text and copied, but it can not be used by anybody or any other client to connect with Push server.

Server Verification: The Server will be verified by Client (Device)

4. Let us talk one step ahead. The Server authentication. Is the Server valid? And authorized (knows your key?)? To do that, we shall perform a reverse test. The client (IoT device) will send a Random no (let us call it KEYRNDDEV). Server should return the HASH(KEYSEC+KEYRNDDEV), and client (IoT device) can verify it. If calculated Hash and sent (by Server) are same, device can ensure that the Server is genuine.

So, how the API will work?

Activate Session Key based communication. Client authentication by dynamic Session
Get Challenge (Client/Device Auth) from server
STCP::ACTIVATE:
Returns: (Server sends the challenge)
OK:
Key Derivation (at Client side):
SESSION_KEY= HASH_MD5(KEY+RANDOM_NO)
Use Session_key for rest of API as it is.

Get Challenge and Get Response (Client/Device Auth+Server Auth)
STCP::ACTIVATE::
Returns:
OK::
RESPONSE verification (at Client): RESPONSE= HASH_MD5(KEY+RANDOM_NO) Key Derivation (at Client side):
SESSION_KEY= HASH_MD5(KEY+RANDOM_NO)
Use Session_key for rest of API as it is.

De-activate Session Key based communication.
STCP::DEACTIVATE:
Returns:
OK:STCP_DEACTIVATED
Use Normal key for rest of API as it is.

Your IoT device is protected from Replay Attack, 100%.

04/03/2017
Now what about Relay Attack??
If there is a man in middle,and it simply relays the session (with cloned Key) with modified data? Oh! Your device will be screwed up.
Relax! Here is the solution.
I know, probably, you will not use my API, but what is the harm to know stuffs 🙂
In typical Relay attack protection, Time Stamp of client is used. Here, we cant do (or do not intend) because of less resource availability and NTP. Rather, we shall use “nonce” or Random Data, once sent from server.

Let us consider “DATA” too, as a parameter of session Key generation function. So, here we are talking about a Dynamic session key where KEYSES= Hash(KEYSEC+RND+DATA). And every-time,with every packet sent to SERVER, the KEYSES will vary based on DATA. Both integrity and secrecy (of secret Key) will be maintained.
Is not exciting?
OK, let us see the API list:

Activate Dynamic-Session Key based communication.
Get Challenge (Client/Device Auth) from server
DSTCP::ACTIVATE
Returns: (Server sends the challenge)
OK:
Key Derivation (at Client side):
DYN_SESSION_KEY= HASH_MD5(KEY+RANDOM_NO+DATA)
Use Dynamic_Session_key for rest of API as it is.

Get Challenge and Get Response (Server Auth)
DSTCP::ACTIVATE:
Returns:
OK::
RESPONSE verification (at Client): RESPONSE= HASH_MD5(KEY+RANDOM_NO) Key Derivation (at Client side):
DYN_SESSION_KEY= HASH_MD5(KEY+RANDOM_NO+DATA)
Use DYN_Session_key for rest of API as it is.

De-activate Session Key based communication.
DSTCP::DEACTIVATE
Returns:
OK:DSTCP_DEACTIVATED
Use Normal key for rest of API as it is.

Now, your Device is fully protected from Relay Attack and Replay Attack.
Cheers!

What next?
Ready to move with Hash-chain?

Keep in touch for further level of security updates..

E-Mail:
How much effort you have given to put IMAP or POP client inside device? Relax! Now the P2PCluster can manage all those. Use simple API to send a mail to anybody anywhere.

SMS:
P2PCluster supports free SMS using low level simple API. Eventually its available only in India. (Wait for API release)

TIME:

You know how complex to sync your device time, either it is a scheduler or alarm ! Relax, now P2PCluster

will help you to get the time stamps in possible all formats.

Use API
TCP:TIME::
Example:
TCP:TIME:11FE313037C5327CD35BAC0123456789:+05:30

VIDEO: AUDIO:Transparent Channel: (23/04/2017)
Its a dream of all cloud access people to create and/or get a transparent route to share our high bandwidth data. So, the requirements are pretty well defined. A client-client transparent channel with possibly high bandwidth. Here it is. New API version released with transparent channel where two client need to connect to PushServer and after couple of (precisely two) raw TCP API call, user will get a transparent channel.
The API for RAW data communication is created for high bandwidth Audio/Video transfer. Currently it supports one-one channel. Exemplary client program (C#) under testing. Once tested, shall share in git.

Server configuration:
The p2pcluster repo is straightforward. Just dump it. Compile it and run.
Before it comes in action you need to feed the port address and Email configuration at Server.conf inside /CONFIG directory

EMAIL={
mail_smtp_host=
mail_transport_protocol=smtp
mail_smtp_auth=true
mail_smtp_port=25
USERNAME=
PASSWORD=
}

The update will be made to work it in offline mode in a LAN environment.

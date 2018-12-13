# MMORPG Covert Channel
### Contributors
Laura Weintraub, Charissa Miller, Joshua Geise
### Version 1.0

## Description
This is a script written in Java that is meant to be utilized as a covert channel in the MMORPG Old School Runescape. This script is meant to be run in a botting client created in this game called DreamBot, and the same script can be ran for both the sender and receiver of the message. The script currently has two different items that correspond to the values 0 and 1. The script starts a trade between two separate players and trades the items in order to transmit a binary message. After the items are put into the trade window, the message is deciphered by the receiver.

## Instructions
1) Download and Set up Dreambot (Can be found at: https://dreambot.org/forums/).
2) Modify the script to contain your desired message and change the usernames to your created Runescape accounts
2) Export the script into a Jar File.
3) Place the Jar File into ../Dreambot/Scripts folder.
4) Start Dreambot and log into both accounts
5) Run the script on each account.

If issues arise, check the Debug Log for more information.


## Resources
To learn more about creating custom scripts for Dreambot, this tutorial is a great place to start:
  https://dreambot.org/forums/index.php?/topic/628-scripting-tutorial-in-depth-no-prior-knowledge-needed-where-to-get-started-by-apaec/

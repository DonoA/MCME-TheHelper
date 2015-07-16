TheHelper
=========
[![Build Status](https://travis-ci.org/DonoA/MCME-TheHelper.svg?branch=master)](https://travis-ci.org/DonoA/MCME-TheHelper)

<i>In-game player helper</i><br/>
<br/>
Author(s): DonoA, q220, aaldim
<br/>

Tracks for new players, and sends valuable information to them, such as the location of the forums, where to find the texturepacks, TeamSpeak and how to use it, ...
Also is used to supply developers with various statistics and debug information.

As of now, the Plugin does the following when welcomeMSG is set to true in the config:

- Checks if the player is a new player or not by verifying if there's a valid profile within the world folder.
- If no profile was found, it broadcasts a message server wide informing everyone that the player is new.
- The new player will receive various information on his first join, such as a link to the forum, and pointing out that Guides are there to help/assist new players.


The following commands are available:

/helper forums | Returns a description and clickable url to the forums.

/helper textures | Returns a clickable url to the resources system.

/helper map | Returns clickable url for the dynmap

/helper servers | Returns a clickable url to the Server List resource page

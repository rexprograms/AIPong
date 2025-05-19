# AI PONG

A vibe coded AI pong game using Junie with very little manual code editing.

* I gave the AI specific step-by-step tasks to create each component/feature one at a time opposed to telling it "make a pong game"
* You can kind of see the history of this in the commit history although I did not commit after each task was done by the AI agent
* I ran out of data volume in the free license an hour or so from completing a working game.
 
## Prompts

The following are the verbatim prompts that I fed to Junie other than a few "manual interventions," the rest of the code was generated for me by the AI agent.  This includes moving files around on the file system in some cases.

#### Bootstrap the app
> I want to make a game, create a main class that will do all of the boiler plate game stuff.  Make it render a graphics window with an orange ball in the middle.  Create additional classes if necessary.

#### Correct mistake
> You put game.kt in org.example, I wanted you to put it in the package I already created, org.rex.junietest

#### Manual Intervention
At this point all of the classes are inside of Game.kt, I manually move them out and fix imports.  I wanted GamePanel to be the main Renderer.  I continued with AI from here and Junie understood me as if it had made my changes itself.

#### Entity class
> Create an entity class to represent entities within the game, give them an x and y coordinate and a render function and collision box as well as collission detection bounding box and ability to specify an oncollision lambda function

#### Rendering entities
> Update GamePanel so that it tracks a list of entities, provide a way of registering entities and unregistering entities.  Update Game so that it holds the ball entity instead of GamePanel and registers it with gamepanel to be rendered.  Make sure that GamePanel renders all entities in it's list of registered entities.

#### Game Loop
> Give the game class a game loop that runs at 60fps, update GamePanel so that it renders indepedently at up to 200fps.

#### FPS Display
> update gamepanel so that it can render FPS, make it show FPS in the top right corner in the format "fps: {fps}" in arial font, size 14

#### Make it so our ball can konw the screen boundaries (useful later)
> Update gamepanel so that it exposes it's width and height as static variables, make it so the user cannot resize the window

#### Ball is too big, also want it to start itself in the center of the screen instead of Game.kt doing this
> Make the ball 50% smaller, make it so that it sets it's own starting position in the center of the screen.

#### Make ball move around the screen
> Add an "update" function to the entity class that accepts a delta time, give ballentity a velocity vector, implement the update function in ballentity  so that it changes it's position based on it's velocity.  Give it a random starting vector but don't make it too fast.  Update the gameloop so that it calls the ball update function

#### Try to make the ball's speed independent of it's velocity vector (it sorta worked...)
> Implement a velocity system in ball, make 100 the max velocity, make it so each time it bounces it's velocity is increased a little bit.  Velocity should be a variable that can be passed into the constructor as well.  Starting velocity should be 50.

#### Speed up the ball
This didn't actually work due to a bug introduced by the AI where the default speed was set to 400 but max speed was still 100...
> The ball is moving way too slow, make it move faster but keep the velocity bounds the same.  It should be able to reach the screen edge in 1 second with it's starting velocity (don't make this a requirement, this is just a generalization) Also shrink ball's size by 50%.

#### Subpixel Rendering
> The ball is too jittery, I think the renderer needs to understand subpixels so that it can move things more smoothly

#### Scoring 
> make it so ball doesn't bounce off the left or right sides, instead it calls a lambda function that is passed in the constructor called "scorePoint"  Game should implement a point scored function that just prints a log message for now of which side scored and pass in a lambda function to ball that calls the point scored function.

#### Fix code issue
> The score point function should take an enum, not a string.  Enum should be SIDE.LEFT or SIDE.RIGHT.  Add a score tracker to Game and increase the correct side when a point is scored.  The log message should also be updated to show the current score.

#### Attempting to fix the ball movement bug by making the code easier to understand and changing how speed applies to the velocity vector 
> Update ball so that the velocity system uses a variable called "ballSpeed" instead.  Make ballSpeed a local variable that increases every time the ball bounces.  make velocityX and velocityY a number between 0 and 1 and when moving the ball, multiply them by ballSpeed.  Whenever the ball collides with something ballSpeed should be increased to it's maximum.

#### Try to fix ballspeed again
I was pretty impressed how it made this change since it did it the way I would have.  It first generated a value for X that had a min of .25, then it subtracted that value from 1 to get the Y velocity
> Make it so X and Y velocity always add up to 1, speed should only be determined by ballSpeed

#### Manual intervention
The ball speed was still broken, I looked at the code and noticed that the starting speed was 400, but the max was 100.  Every time the ball bounced, it just slowed down to 100.  I changed this by making the max speed 1000 and left the starting speed at 400.

#### Out of credits -- Switching to Cursor
I ran out of credits with Junie so at this point I switch to cursor
> Create a new class that extends entity called NetEntity, it should be centered on the screen and when it renders it should create a vertical white line 5 pixels wide from the top of the screen to the bottom

#### Add score to the screen
> Add a textentity that extends entity, it should have an update function to update the text that it displays, by default it should not show any text.  Then add a textentity to the game for the left side and the right side, position the text entitites centered at the top to the left and the right of the net.  Update the text entities with the current score for that side.
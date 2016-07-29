<h1>RPGChars 2016
by Stefan “1of3” Koch</h1>


<h2>BASICS</h2>

You can use this program to manage characters and their abilities for a tabletop Roleplaying Game. This is probably most useful for GMs.

You can select from a number of common game systems. (Customizing might be added later.)

Use the menu to create characters and abilities. When you select a character on the tree and hit CHECK SELECTED, it will roll the ability according to the system. When you select several abilities all of them will be checked and sorted by result.

When you check a character, all associated abilities will be checked. You can use this to create lists of abilities as pseudo-characters. Say, you want to roll perception or initiative for all PCs. Just make a Perception (or Initiative) “character” and check it.

In the same way an ability doesn’t have to be a single stat in the game system. For example, you can make separate abilities for attacks with and without the character’s usual buffs.

<h2>ENTERING ABILITY VALUES</h2>

The notation for abilities is meant to be as simple as possible.

Some games use separate attack and damage rolls. You enter both in the same field, separated by comma ( , ). The program will recognise what do with “+5, 4d8+3” in a D&D campaign,for example.

For games that only use a single type of die, you don’t have to specify to size of dice. In a Shadowrun “3”, “3d” and “3d6” will all give you three six-siders.

Some games modify their system somewhat for different characters or abilities. For example in nWoD you can generally get another die, when you roll ten (“10-again”). But some actions have 9-again or 8-again. You can get to these alternative systems by adding an exclamation mark to your value. So in a nWoD game, “4” will give you 4d10 at 10-again, while “4!” will make it 4d10 with 9-again and “4!!” will give you 8-again.

Some games require another parameter to roll dice. For example, Roll&Keep wants to know the number of dice and how many dice to keep. In such a case, enter further parameters after a dollar sign ($). So in 7th Sea, “4$3” will give “4d10 keep 3”

If you use a system that uses derived modifier for the actually rolling (like ability modifiers in D&D or step dice in Earthdawn) only use the derived modifier. You can enter the original number in the abilities name or info field.

If you want to overwrite the current game system and simply roll the dice, add a question mark (?) to your input. It doesn’t matter where you put your exclamation marks and percent signs, as long as you do not move them beyond a comma. In that case you modify the second roll. It is technically possible to make a third or fourth roll. If for a certain position no dice mechanic is defined, the program will simply roll the dice.

You can use the same notation in the INDEPENDENT CHECK area.

<h2>USING THE NAMING PATTERN</h2>
To use a name pattern make sure the file called “rpgchars_names.xml” is located in the same folder as your .jar file.

When you select a pattern under Campaign > Naming Pattern random characters will get a random name according to the chosen pattern. If you do not select a pattern, all random characters will be named Alrik. (There are just so many of them…)

If you want to create your own pattern add the following to .xml.

<code>
&lt;namepatternslist&gt;
…

&lt;pattern id=”my_pattern”&gt;

&lt;component&gt; Enter First Names Here Separated By Spaces &lt;/component&gt;

&lt;component&gt;Last Names Or Whatever Here&lt;/component&gt;

&lt;component&gt;You Can Use Any Number Of Components&lt;/component&gt;

&lt;component&gt;Underscores Are_Removed. Use Them To_Have Multi-part_Names&lt;/components&gt;

&lt;!-- Structures include the rules for the pattern. --&gt;

&lt;structure&gt;$0 $1&lt;/structure&gt;

&lt;!-- Output: One word from the first component, then a space, then one from the second component ?

&lt;structure&gt;$0, child of $0&lt;/structure&gt;

&lt;!-- Output: Some word from the first component, followed by “, child of” followed by another name. ?

&lt;structure ratio=”7”&gt;/2&lt;/structure&gt;

&lt;!-- The ratio=”7” attribute makes this structure seven times more likely than unmarked structures. The /2 will output an item from the third component and make sure the word is capitalized. --&gt;

&lt;structure&gt;\1&lt;/structure&gt;

&lt;!-- A backslash will ensure the word is decapitalized. --&gt;

&lt;/pattern&gt;

....
&lt;/namepatternslist&gt;
</code>


Have fun gaming and please report bugs, as you find them.

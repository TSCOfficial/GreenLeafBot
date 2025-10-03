# How to add a new action to the interaction infrastructure

## Step 1: New action class

1. Create a new java class inside `ch.greenleaf/interactions/actions/list`.
2. Give that new action an ID, i.e. `/message/send`, `/message/edit`, ...
    ```
    public static final String ID = "/base/action";
    ```
3. Add the standard stuff, such es a **Constructor**, the `fetchDatabase()` Method and the `execute()` method

## Step 2: Connect the action to the system
1. Append the new action to the ActionType Enum, using the action ID (see step 2) and the database reference table from which the data will get fetched
2. Add the new action class in the **ActionList** file
3. Connect the Type with the class in the **ActionRegistry**

## Step 3: Insert new action in database
1. Insert the new action ID in the database, inside the `action_type` table
2. Insert any action variable in the `action_type_variable` table
/*
 *  Copyright 2004-2006 Stefan Reuter
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package org.asteriskjava.fastagi.command;

/**
 * Deletes a family or specific keytree within a family in the Asterisk
 * database.<p>
 * Returns 1 if successful, 0 otherwise.
 * 
 * @author srt
 * @version $Id: DatabaseDelCommand.java,v 1.1 2010/09/19 19:11:22 yusmery Exp $
 */
public class DatabaseDelCommand extends AbstractAgiCommand
{
    /**
     * Serial version identifier.
     */
    private static final long serialVersionUID = 3256719598056387384L;

    /**
     * The family (or family of the keytree) to delete.
     */
    private String family;

    /**
     * The keyTree to delete.
     */
    private String keyTree;

    /**
     * Creates a new DatabaseDelCommand to delete a family.
     * 
     * @param family the family to delete.
     */
    public DatabaseDelCommand(String family)
    {
        super();
        this.family = family;
    }

    /**
     * Creates a new DatabaseDelCommand to delete a keytree.
     * 
     * @param family the family of the keytree to delete.
     * @param keyTree the keytree to delete.
     */
    public DatabaseDelCommand(String family, String keyTree)
    {
        super();
        this.family = family;
        this.keyTree = keyTree;
    }

    /**
     * Returns the family (or family of the keytree) to delete.
     * 
     * @return the family (or family of the keytree) to delete.
     */
    public String getFamily()
    {
        return family;
    }

    /**
     * Sets the family (or family of the keytree) to delete.
     * 
     * @param family the family (or family of the keytree) to delete.
     */
    public void setFamily(String family)
    {
        this.family = family;
    }

    /**
     * Returns the the keytree to delete.
     * 
     * @return the keytree to delete.
     */
    public String getKeyTree()
    {
        return keyTree;
    }

    /**
     * Sets the keytree to delete.
     * 
     * @param keyTree the keytree to delete.
     */
    public void setKeyTree(String keyTree)
    {
        this.keyTree = keyTree;
    }

    @Override
   public String buildCommand()
    {
        return "DATABASE DELTREE " + escapeAndQuote(family)
                + (keyTree == null ? "" : " " + escapeAndQuote(keyTree));
    }
}

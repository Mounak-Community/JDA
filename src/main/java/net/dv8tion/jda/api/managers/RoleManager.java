/*
 * Copyright 2015-2019 Austin Keener, Michael Ritter, Florian Spieß, and the JDA contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.dv8tion.jda.api.managers;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.internal.utils.Checks;

import javax.annotation.CheckReturnValue;
import java.awt.Color;
import java.util.Arrays;
import java.util.Collection;

/**
 * Manager providing functionality to update one or more fields for a {@link net.dv8tion.jda.api.entities.Role Role}.
 *
 * <p><b>Example</b>
 * <pre>{@code
 * manager.setName("Administrator")
 *        .setColor(null)
 *        .queue();
 * manager.reset(RoleManager.PERMISSION | RoleManager.NAME)
 *        .setName("Traitor")
 *        .setColor(Color.RED)
 *        .queue();
 * }</pre>
 *
 * @see net.dv8tion.jda.api.entities.Role#getManager()
 */
public interface RoleManager extends Manager<RoleManager>
{
    /** Used to reset the name field */
    long NAME        = 0x1;
    /** Used to reset the color field */
    long COLOR       = 0x2;
    /** Used to reset the permission field */
    long PERMISSION  = 0x4;
    /** Used to reset the hoisted field */
    long HOIST       = 0x8;
    /** Used to reset the mentionable field */
    long MENTIONABLE = 0x10;

    /**
     * Resets the fields specified by the provided bit-flag pattern.
     * You can specify a combination by using a bitwise OR concat of the flag constants.
     * <br>Example: {@code manager.reset(RoleManager.COLOR | RoleManager.NAME);}
     *
     * <p><b>Flag Constants:</b>
     * <ul>
     *     <li>{@link #NAME}</li>
     *     <li>{@link #COLOR}</li>
     *     <li>{@link #PERMISSION}</li>
     *     <li>{@link #HOIST}</li>
     *     <li>{@link #MENTIONABLE}</li>
     * </ul>
     *
     * @param  fields
     *         Integer value containing the flags to reset.
     *
     * @return RoleManager for chaining convenience
     */
    @Override
    RoleManager reset(long fields);

    /**
     * Resets the fields specified by the provided bit-flag patterns.
     * You can specify a combination by using a bitwise OR concat of the flag constants.
     * <br>Example: {@code manager.reset(RoleManager.COLOR, RoleManager.NAME);}
     *
     * <p><b>Flag Constants:</b>
     * <ul>
     *     <li>{@link #NAME}</li>
     *     <li>{@link #COLOR}</li>
     *     <li>{@link #PERMISSION}</li>
     *     <li>{@link #HOIST}</li>
     *     <li>{@link #MENTIONABLE}</li>
     * </ul>
     *
     * @param  fields
     *         Integer values containing the flags to reset.
     *
     * @return RoleManager for chaining convenience
     */
    @Override
    RoleManager reset(long... fields);

    /**
     * The target {@link net.dv8tion.jda.api.entities.Role Role} for this
     * manager
     *
     * @return The target Role
     */
    Role getRole();

    /**
     * The {@link net.dv8tion.jda.api.entities.Guild Guild} this Manager's
     * {@link net.dv8tion.jda.api.entities.Role Role} is in.
     * <br>This is logically the same as calling {@code getRole().getGuild()}
     *
     * @return The parent {@link net.dv8tion.jda.api.entities.Guild Guild}
     */
    default Guild getGuild()
    {
        return getRole().getGuild();
    }

    /**
     * Sets the <b><u>name</u></b> of the selected {@link net.dv8tion.jda.api.entities.Role Role}.
     *
     * <p>A role name <b>must not</b> be {@code null} nor less than 1 characters or more than 32 characters long!
     *
     * @param  name
     *         The new name for the selected {@link net.dv8tion.jda.api.entities.Role Role}
     *
     * @throws IllegalArgumentException
     *         If the provided name is {@code null} or not between 1-100 characters long
     *
     * @return RoleManager for chaining convenience
     */
    @CheckReturnValue
    RoleManager setName(String name);

    /**
     * Sets the {@link net.dv8tion.jda.api.Permission Permissions} of the selected {@link net.dv8tion.jda.api.entities.Role Role}.
     *
     * <p>Permissions may only include already present Permissions for the currently logged in account.
     * <br>You are unable to give permissions you don't have!
     *
     * @param  perms
     *         The new raw permission value for the selected {@link net.dv8tion.jda.api.entities.Role Role}
     *
     * @throws net.dv8tion.jda.api.exceptions.InsufficientPermissionException
     *         If the currently logged in account does not have permission to apply one of the specified permissions
     *
     * @return RoleManager for chaining convenience
     *
     * @see    #setPermissions(Collection)
     * @see    #setPermissions(Permission...)
     */
    @CheckReturnValue
    RoleManager setPermissions(long perms);

    /**
     * Sets the {@link net.dv8tion.jda.api.Permission Permissions} of the selected {@link net.dv8tion.jda.api.entities.Role Role}.
     *
     * <p>Permissions may only include already present Permissions for the currently logged in account.
     * <br>You are unable to give permissions you don't have!
     *
     * @param  permissions
     *         The new permission for the selected {@link net.dv8tion.jda.api.entities.Role Role}
     *
     * @throws net.dv8tion.jda.api.exceptions.InsufficientPermissionException
     *         If the currently logged in account does not have permission to apply one of the specified permissions
     * @throws java.lang.IllegalArgumentException
     *         If any of the provided values is {@code null}
     *
     * @return RoleManager for chaining convenience
     *
     * @see    #setPermissions(Collection)
     * @see    #setPermissions(long)
     * @see    net.dv8tion.jda.api.Permission#getRaw(net.dv8tion.jda.api.Permission...) Permission.getRaw(Permission...)
     */
    @CheckReturnValue
    default RoleManager setPermissions(Permission... permissions)
    {
        Checks.notNull(permissions, "Permissions");
        return setPermissions(Arrays.asList(permissions));
    }

    /**
     * Sets the {@link net.dv8tion.jda.api.Permission Permissions} of the selected {@link net.dv8tion.jda.api.entities.Role Role}.
     *
     * <p>Permissions may only include already present Permissions for the currently logged in account.
     * <br>You are unable to give permissions you don't have!
     *
     * @param  permissions
     *         The new permission for the selected {@link net.dv8tion.jda.api.entities.Role Role}
     *
     * @throws net.dv8tion.jda.api.exceptions.InsufficientPermissionException
     *         If the currently logged in account does not have permission to apply one of the specified permissions
     * @throws java.lang.IllegalArgumentException
     *         If any of the provided values is {@code null}
     *
     * @return RoleManager for chaining convenience
     *
     * @see    #setPermissions(Permission...)
     * @see    #setPermissions(long)
     * @see    java.util.EnumSet EnumSet
     * @see    net.dv8tion.jda.api.Permission#getRaw(java.util.Collection) Permission.getRaw(Collection)
     */
    @CheckReturnValue
    default RoleManager setPermissions(Collection<Permission> permissions)
    {
        Checks.noneNull(permissions, "Permissions");
        return setPermissions(Permission.getRaw(permissions));
    }

    /**
     * Sets the {@link java.awt.Color Color} of the selected {@link net.dv8tion.jda.api.entities.Role Role}.
     *
     * @param  color
     *         The new color for the selected {@link net.dv8tion.jda.api.entities.Role Role}
     *
     * @return RoleManager for chaining convenience
     */
    @CheckReturnValue
    default RoleManager setColor(Color color)
    {
        return setColor(color == null ? Role.DEFAULT_COLOR_RAW : color.getRGB());
    }

    /**
     * Sets the rgb color of the selected {@link net.dv8tion.jda.api.entities.Role Role}.
     *
     * @param  rgb
     *         The new color for the selected {@link net.dv8tion.jda.api.entities.Role Role}
     *
     * @return RoleManager for chaining convenience
     *
     * @see    Role#DEFAULT_COLOR_RAW Role.DEFAULT_COLOR_RAW
     */
    @CheckReturnValue
    RoleManager setColor(int rgb);

    /**
     * Sets the <b><u>hoist state</u></b> of the selected {@link net.dv8tion.jda.api.entities.Role Role}.
     *
     * @param  hoisted
     *         Whether the selected {@link net.dv8tion.jda.api.entities.Role Role} should be hoisted
     *
     * @return RoleManager for chaining convenience
     */
    @CheckReturnValue
    RoleManager setHoisted(boolean hoisted);

    /**
     * Sets the <b><u>mentionable state</u></b> of the selected {@link net.dv8tion.jda.api.entities.Role Role}.
     *
     * @param  mentionable
     *         Whether the selected {@link net.dv8tion.jda.api.entities.Role Role} should be mentionable
     *
     * @return RoleManager for chaining convenience
     */
    @CheckReturnValue
    RoleManager setMentionable(boolean mentionable);

    /**
     * Adds the specified {@link net.dv8tion.jda.api.Permission Permissions} to the selected {@link net.dv8tion.jda.api.entities.Role Role}.
     *
     * <p>Permissions may only include already present Permissions for the currently logged in account.
     * <br>You are unable to give permissions you don't have!
     *
     * @param  perms
     *         The permission to give to the selected {@link net.dv8tion.jda.api.entities.Role Role}
     *
     * @throws net.dv8tion.jda.api.exceptions.InsufficientPermissionException
     *         If the currently logged in account does not have permission to apply one of the specified permissions
     *
     * @return RoleManager for chaining convenience
     *
     * @see    #setPermissions(Collection)
     * @see    #setPermissions(Permission...)
     * @see    net.dv8tion.jda.api.Permission#getRaw(net.dv8tion.jda.api.Permission...) Permission.getRaw(Permission...)
     */
    @CheckReturnValue
    default RoleManager givePermissions(Permission... perms)
    {
        Checks.notNull(perms, "Permissions");
        return givePermissions(Arrays.asList(perms));
    }

    /**
     * Adds the specified {@link net.dv8tion.jda.api.Permission Permissions} to the selected {@link net.dv8tion.jda.api.entities.Role Role}.
     *
     * <p>Permissions may only include already present Permissions for the currently logged in account.
     * <br>You are unable to give permissions you don't have!
     *
     * @param  perms
     *         The permission to give to the selected {@link net.dv8tion.jda.api.entities.Role Role}
     *
     * @throws net.dv8tion.jda.api.exceptions.InsufficientPermissionException
     *         If the currently logged in account does not have permission to apply one of the specified permissions
     *
     * @return RoleManager for chaining convenience
     *
     * @see    #setPermissions(Collection)
     * @see    #setPermissions(Permission...)
     * @see    java.util.EnumSet EnumSet
     * @see    net.dv8tion.jda.api.Permission#getRaw(java.util.Collection) Permission.getRaw(Collection)
     */
    @CheckReturnValue
    RoleManager givePermissions(Collection<Permission> perms);

    /**
     * Revokes the specified {@link net.dv8tion.jda.api.Permission Permissions} from the selected {@link net.dv8tion.jda.api.entities.Role Role}.
     *
     * <p>Permissions may only include already present Permissions for the currently logged in account.
     * <br>You are unable to revoke permissions you don't have!
     *
     * @param  perms
     *         The permission to give to the selected {@link net.dv8tion.jda.api.entities.Role Role}
     *
     * @throws net.dv8tion.jda.api.exceptions.InsufficientPermissionException
     *         If the currently logged in account does not have permission to revoke one of the specified permissions
     *
     * @return RoleManager for chaining convenience
     *
     * @see    #setPermissions(Collection)
     * @see    #setPermissions(Permission...)
     * @see    net.dv8tion.jda.api.Permission#getRaw(net.dv8tion.jda.api.Permission...) Permission.getRaw(Permission...)
     */
    @CheckReturnValue
    default RoleManager revokePermissions(Permission... perms)
    {
        Checks.notNull(perms, "Permissions");
        return revokePermissions(Arrays.asList(perms));
    }


    /**
     * Revokes the specified {@link net.dv8tion.jda.api.Permission Permissions} from the selected {@link net.dv8tion.jda.api.entities.Role Role}.
     *
     * <p>Permissions may only include already present Permissions for the currently logged in account.
     * <br>You are unable to revoke permissions you don't have!
     *
     * @param  perms
     *         The permission to give to the selected {@link net.dv8tion.jda.api.entities.Role Role}
     *
     * @throws net.dv8tion.jda.api.exceptions.InsufficientPermissionException
     *         If the currently logged in account does not have permission to revoke one of the specified permissions
     *
     * @return RoleManager for chaining convenience
     *
     * @see    #setPermissions(Collection)
     * @see    #setPermissions(Permission...)
     * @see    java.util.EnumSet EnumSet
     * @see    net.dv8tion.jda.api.Permission#getRaw(java.util.Collection) Permission.getRaw(Collection)
     */
    @CheckReturnValue
    RoleManager revokePermissions(Collection<Permission> perms);
}

package edu.nps.moves.dis7;

import java.util.*;
import java.io.*;
import edu.nps.moves.disenum.*;
import edu.nps.moves.disutil.*;

// Jaxb and Hibernate annotations generally won't work on mobile devices. XML serialization uses jaxb, and
// javax.persistence uses the JPA JSR, aka hibernate. See the Hibernate site for details.
// To generate Java code without these, and without the annotations scattered through the
// see the XMLPG java code generator, and set the boolean useHibernateAnnotations and useJaxbAnnotions 
// to false, and then regenerate the code

import javax.xml.bind.*;            // Used for JAXB XML serialization
import javax.xml.bind.annotation.*; // Used for XML serialization annotations (the @ stuff)
import javax.persistence.*;         // Used for JPA/Hibernate SQL persistence

/**
 * The unique designation of each entity in an event or exercise that is contained in a Live Entity PDU. Section 6.2.54 
 *
 * Copyright (c) 2008-2014, MOVES Institute, Naval Postgraduate School. All rights reserved.
 * This work is licensed under the BSD open source license, available at https://www.movesinstitute.org/licenses/bsd.html
 *
 * @author DMcG
 */
@Entity  // Hibernate
@Inheritance(strategy=InheritanceType.JOINED)  // Hibernate
public class LiveEntityIdentifier extends Object implements Serializable
{
   /** Primary key for hibernate, not part of the DIS standard */
   private long pk_LiveEntityIdentifier;

   /** Live Simulation Address record (see 6.2.54)  */
   protected LiveSimulationAddress  liveSimulationAddress = new LiveSimulationAddress(); 

   /** Live entity number  */
   protected int  entityNumber;


/** Constructor */
 public LiveEntityIdentifier()
 {
 }

@Transient  // Marked as transient to prevent hibernate from thinking this is a persistent property
public int getMarshalledSize()
{
   int marshalSize = 0; 

   marshalSize = marshalSize + liveSimulationAddress.getMarshalledSize();  // liveSimulationAddress
   marshalSize = marshalSize + 2;  // entityNumber

   return marshalSize;
}


/** Primary key for hibernate, not part of the DIS standard */
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
public long getPk_LiveEntityIdentifier()
{
   return pk_LiveEntityIdentifier;
}

/** Hibernate primary key, not part of the DIS standard */
public void setPk_LiveEntityIdentifier(long pKeyName)
{
   this.pk_LiveEntityIdentifier = pKeyName;
}

public void setLiveSimulationAddress(LiveSimulationAddress pLiveSimulationAddress)
{ liveSimulationAddress = pLiveSimulationAddress;
}

// HIBERNATE: this ivar is a foreign key, linked to the below class table. 
// It is not a DIS-standard variable and is not marshalled to IEEE-1278.1
public long fk_liveSimulationAddress;

@XmlElement
@OneToOne(cascade = CascadeType.ALL)
@JoinColumn(name="fk_liveSimulationAddress")
public LiveSimulationAddress getLiveSimulationAddress()
{ return liveSimulationAddress; 
}

public void setEntityNumber(int pEntityNumber)
{ entityNumber = pEntityNumber;
}

@XmlAttribute // Jaxb
@Basic       // Hibernate
public int getEntityNumber()
{ return entityNumber; 
}


public void marshal(DataOutputStream dos)
{
    try 
    {
       liveSimulationAddress.marshal(dos);
       dos.writeShort( (short)entityNumber);
    } // end try 
    catch(Exception e)
    { 
      System.out.println(e);}
    } // end of marshal method

public void unmarshal(DataInputStream dis)
{
    try 
    {
       liveSimulationAddress.unmarshal(dis);
       entityNumber = (int)dis.readUnsignedShort();
    } // end try 
   catch(Exception e)
    { 
      System.out.println(e); 
    }
 } // end of unmarshal method 


/**
 * Packs a Pdu into the ByteBuffer.
 * @throws java.nio.BufferOverflowException if buff is too small
 * @throws java.nio.ReadOnlyBufferException if buff is read only
 * @see java.nio.ByteBuffer
 * @param buff The ByteBuffer at the position to begin writing
 * @since ??
 */
public void marshal(java.nio.ByteBuffer buff)
{
       liveSimulationAddress.marshal(buff);
       buff.putShort( (short)entityNumber);
    } // end of marshal method

/**
 * Unpacks a Pdu from the underlying data.
 * @throws java.nio.BufferUnderflowException if buff is too small
 * @see java.nio.ByteBuffer
 * @param buff The ByteBuffer at the position to begin reading
 * @since ??
 */
public void unmarshal(java.nio.ByteBuffer buff)
{
       liveSimulationAddress.unmarshal(buff);
       entityNumber = (int)(buff.getShort() & 0xFFFF);
 } // end of unmarshal method 


 /*
  * The equals method doesn't always work--mostly it works only on classes that consist only of primitives. Be careful.
  */
@Override
 public boolean equals(Object obj)
 {

    if(this == obj){
      return true;
    }

    if(obj == null){
       return false;
    }

    if(getClass() != obj.getClass())
        return false;

    return equalsImpl(obj);
 }

 /**
  * Compare all fields that contribute to the state, ignoring
 transient and static fields, for <code>this</code> and the supplied object
  * @param obj the object to compare to
  * @return true if the objects are equal, false otherwise.
  */
 public boolean equalsImpl(Object obj)
 {
     boolean ivarsEqual = true;

    if(!(obj instanceof LiveEntityIdentifier))
        return false;

     final LiveEntityIdentifier rhs = (LiveEntityIdentifier)obj;

     if( ! (liveSimulationAddress.equals( rhs.liveSimulationAddress) )) ivarsEqual = false;
     if( ! (entityNumber == rhs.entityNumber)) ivarsEqual = false;

    return ivarsEqual;
 }
} // end of class

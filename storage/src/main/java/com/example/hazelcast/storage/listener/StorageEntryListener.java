package com.example.hazelcast.storage.listener;

import com.hazelcast.core.EntryEvent;
import com.hazelcast.core.EntryListener;
import com.hazelcast.core.MapEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StorageEntryListener implements EntryListener {

    final static Logger logger = LoggerFactory.getLogger(StorageEntryListener.class);

    @Override
    public void entryAdded(EntryEvent entryEvent) {
        if(entryEvent.getMember().localMember())
            logger.debug( "Entry Added: " + entryEvent.getName() + ": " +entryEvent.getKey() );
    }

    @Override
    public void entryEvicted(EntryEvent entryEvent) {
        if(entryEvent.getMember().localMember())
            logger.debug( "Entry Evicted: " + entryEvent.getName() + ": " +entryEvent.getKey() );
    }

    @Override
    public void entryRemoved(EntryEvent entryEvent) {
        if(entryEvent.getMember().localMember())
            logger.debug( "Entry Removed: " + entryEvent.getName() + ": " +entryEvent.getKey() );
    }

    @Override
    public void entryUpdated(EntryEvent entryEvent) {
        if(entryEvent.getMember().localMember())
            logger.debug( "Entry Updated: " + entryEvent.getName() + ": " +entryEvent.getKey() );
    }

    @Override
    public void mapCleared(MapEvent mapEvent) {
        if(mapEvent.getMember().localMember())
            logger.debug( "Map Cleared:" + mapEvent );
    }

    @Override
    public void mapEvicted(MapEvent mapEvent) {
        if(mapEvent.getMember().localMember())
            logger.debug( "Map Evicted:" + mapEvent );
    }
}

package com.singaiah.aem.core.models;

import static org.apache.sling.api.resource.ResourceResolver.PROPERTY_RESOURCE_TYPE;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.day.cq.commons.inherit.HierarchyNodeInheritanceValueMap;
import com.day.cq.commons.inherit.InheritanceValueMap;

import java.util.Optional;

@Model(adaptables = Resource.class)
public class Inheritance {

    @ValueMapValue(name=PROPERTY_RESOURCE_TYPE, injectionStrategy=InjectionStrategy.OPTIONAL)
    @Default(values="No resourceType")
    protected String resourceType;


    @SlingObject
    private Resource currentResource;
    
    @SlingObject
    private ResourceResolver resolver;
    
    
    @Inject @Default(values = "")
    private String inheritance = "";

    private String inheritancePath;

    @PostConstruct
    protected void init() 
    {
        String path = Optional.ofNullable(currentResource)
                .map(Resource::getPath).orElse("");

        path         = StringUtils.substringBefore(path, "/jcr:content");
        Resource res = resolver.getResource(path);
        
        InheritanceValueMap iProps = new HierarchyNodeInheritanceValueMap(res);
        String[] elements          = inheritance.split("/");
        inheritancePath            = iProps.getInherited("inheritancePath", String.class) + "/" + elements[elements.length - 1];
        
        return;
        
    } //init


    public String getInheritance() 
    {
    	return inheritance;
    	
    } //getInheritance


	public String getInheritancePath() 
	{
		return inheritancePath;
	}
	
} //Inheritance

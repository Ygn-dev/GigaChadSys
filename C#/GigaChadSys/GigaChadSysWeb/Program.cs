using GigaChadSys.Servicios;
using GigaChadSysWeb.Components;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.
builder.Services.AddRazorComponents()
    .AddInteractiveServerComponents();

// Add global HttpClient pointing to the Java REST Backend
builder.Services.AddScoped(sp => new HttpClient { 
    BaseAddress = new Uri("http://localhost:8080/GigaChadSys-REST-1.0-SNAPSHOT/webresources/") 
});

// Registrar todos los servicios REST que consumen el backend Java
builder.Services.AddGigaChadSysServicios();

var app = builder.Build();

// Configure the HTTP request pipeline.
if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Error", createScopeForErrors: true);
    // The default HSTS value is 30 days. You may want to change this for production scenarios, see https://aka.ms/aspnetcore-hsts.
    app.UseHsts();
}
app.UseStatusCodePagesWithReExecute("/not-found", createScopeForStatusCodePages: true);
app.UseHttpsRedirection();

app.UseAntiforgery();

app.MapStaticAssets();
app.MapRazorComponents<App>()
    .AddInteractiveServerRenderMode();

app.Run();
